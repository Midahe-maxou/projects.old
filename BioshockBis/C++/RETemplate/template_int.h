#pragma once
#ifndef RE_TEMPLATE_INT
#define RE_TEMPLATE_INT

#include "template.h"

#ifdef RE_INT


#include <Windows.h>
#include <vector>
#include <cassert>


#define CONCAT_IMPL(x, y) x##y
#define MACRO_CONCAT(x, y) CONCAT_IMPL(x, y)
#define PAD(SIZE) char MACRO_CONCAT(_pad, __COUNTER__)[SIZE];

/**
* Use in a union to define multiple variables with absolute padding
*/

#define PAD_VARIABLE(type, name, offset)					\
struct {													\
	PAD(offset);											\
	type name;												\
}


#define CREATE_DYNAMIC_GETTER(type, name, offset)															\
			type get##name##()																				\
			{																								\
				return *(type*)(getAddr() + offset);														\
			}

#define CREATE_DYNAMIC_SETTER(type, name, offset)															\
			void set##name##(type val)																		\
			{																								\
				*(type*)(getAddr() + offset) = val;															\
			}
/**
* Use to access variables via functions
*/
#define POINTER_VAR(type, name, offset)																		\
			CREATE_DYNAMIC_GETTER(type, name, offset)														\
			CREATE_DYNAMIC_SETTER(type, name, offset)

/* Alias */
#define POINTER_RO_VAR CREATE_DYNAMIC_GETTER


template<class T>
T FindAddrWithOffsets(_In_ Addr address, _In_ const OFFSET_LIST& offsetList)
{
	size_t nbOffset = offsetList.size();

	if (nbOffset == 0) return (T)address;

	for (unsigned int i = 0; i < nbOffset; ++i) {
		if (!address) return (T)0;
		Addr* ad2 = (Addr*)(address + offsetList[i]);
		address = *ad2;
	}

	return (T)(address);
}


template<class T>
void WriteProtectedPage(_In_ T* dest, const T& data)
{
	DWORD oldProtect;
	VirtualProtect(dest, sizeof(T), PAGE_EXECUTE_READWRITE, &oldProtect);
	*dest = data;
	VirtualProtect(dest, sizeof(T), oldProtect, &oldProtect);
}

class Pointer {
	Addr m_address;
	OFFSET_LIST m_offsets;

protected:
	Pointer() : m_address(0), m_offsets({}) {}

public:
	Pointer(_In_ Addr address, _In_ OFFSET_LIST offsets = {})
		:m_address(address), m_offsets(offsets) {
	}

	inline Addr getAddr() const { return FindAddrWithOffsets<Addr>(m_address, m_offsets); }
};

/* -- Calling conventions definition -- */

struct Stdcall {
	template <typename Ret, typename... Args>
	using FuncPtr = Ret(__stdcall*) (Args...);
};

struct Cdecl {
	template <typename Ret, typename... Args>
	using FuncPtr = Ret(__cdecl*) (Args...);
};

struct Fastcall {
	template <typename Ret, typename... Args>
	using FuncPtr = Ret(__fastcall*) (Args...);
};

struct Vectorcall {
	template <typename Ret, typename... Args>
	using FuncPtr = Ret(__vectorcall*) (Args...);
};

enum class CallingConvention {
	_STDCALL,
	_CDECL,
	_FASTCALL,
	_VECTORCALL
};

template<CallingConvention CC>
struct CallingConventionTrait;

template<>
struct CallingConventionTrait<CallingConvention::_STDCALL> { using type = Stdcall; };

template<>
struct CallingConventionTrait<CallingConvention::_CDECL> { using type = Cdecl; };

template<>
struct CallingConventionTrait<CallingConvention::_FASTCALL> { using type = Fastcall; };

template<>
struct CallingConventionTrait<CallingConvention::_VECTORCALL> { using type = Vectorcall; };


/* -- Function class implementation -- */

template<typename Ret, CallingConvention CC, typename... Args>
class FunctionImpl {
protected:
	using FuncPtr = typename CallingConventionTrait<CC>::type ::template FuncPtr<Ret, Args...>;
	Addr m_addr;

public:
	FunctionImpl(Addr addr = 0) : m_addr(addr) {}
	FunctionImpl(FuncPtr func)
		: m_addr((Addr)func) {
	}

	inline Addr getAddr() const noexcept { return m_addr; }
	inline Ret operator()(Args... args) {
		FuncPtr func;
		*(Addr*)&func = this->m_addr;
		return func(args...);
	}
};


/* --- Function struct template --- */

template <typename Signature>
struct Function;


/* -- Function overload regarding calling convention -- */

template <class Ret, typename... Args>
struct Function<Ret __stdcall (Args...)> : public FunctionImpl<Ret, CallingConvention::_STDCALL, Args...> {
	using Base = FunctionImpl<Ret, CallingConvention::_STDCALL, Args...>;
	using Base::Base;
};

template <class Ret, typename... Args>
struct Function<Ret __cdecl (Args...)> : public FunctionImpl<Ret, CallingConvention::_CDECL, Args...> {
	using Base = FunctionImpl<Ret, CallingConvention::_CDECL, Args...>;
	using Base::Base;
};

template <class Ret, typename... Args>
struct Function<Ret __fastcall (Args...)> : public FunctionImpl<Ret, CallingConvention::_FASTCALL, Args...> {
	using Base = FunctionImpl<Ret, CallingConvention::_FASTCALL, Args...>;
	using Base::Base;
};

template <class Ret, typename... Args>
struct Function<Ret __vectorcall (Args...)> : public FunctionImpl<Ret, CallingConvention::_VECTORCALL, Args...> {
	using Base = FunctionImpl<Ret, CallingConvention::_VECTORCALL, Args...>;
	using Base::Base;
};


/* -- Member Function overload -- */

template<class Instance, class Ret, typename... Args>
class Function<Ret(Instance::*)(Args...)>
{
protected:
	typedef Ret(Instance::* FuncPtr)(Args...);
	Addr m_addr;

public:

	Function(Addr addr = 0) requires (sizeof(FuncPtr) == sizeof(Addr))
		: m_addr(addr) {
	}

	Function(FuncPtr func) requires (sizeof(FuncPtr) == sizeof(Addr))
		: m_addr(*(Addr*)&func) {
	}

	inline Addr getAddr() const noexcept { return m_addr; }

	Ret operator()(Instance* instance, Args... args) {
		FuncPtr func;
		*(Addr*)&func = m_addr;
		return (instance->*func)(std::forward<Args>(args)...);
	}
};


template<class Instance, class Ret, typename... Args>
class BoundFunction : public Function<Ret(Instance::*)(Args...)>
{
	using Base = Function<Ret(Instance::*)(Args...)>;
	Instance* m_callingClass;
	typedef Ret(Instance::* FuncPtr)(Args...);

public:

	BoundFunction(Addr addr, Instance* callingClass) requires (sizeof(FuncPtr) == sizeof(Addr))
		: Base(addr), m_callingClass(callingClass) {
	}

	BoundFunction(FuncPtr func, Instance* callingClass) requires (sizeof(FuncPtr) == sizeof(Addr))
		: Base(func), m_callingClass(callingClass) {
	}

	Ret operator()(Args... args) {
		if (m_callingClass == 0) return (Ret)0;
		FuncPtr func;
		*(Addr*)&func = this->m_addr;
		return (m_callingClass->*func)(std::forward<Args>(args)...);
	}
};

static Function<void()> middleFunc{};
static Addr originalFunc = 0;


inline void callInTheMiddle();

bool hijackCall(Addr callAddr, Function<void()> hackFunc);
void unhook(Addr callAddr);



#endif // RE_INT

#endif // RE_TEMPLATE_INT