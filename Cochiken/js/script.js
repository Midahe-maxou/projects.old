
//ajout d'un evenement: lorsque la page scroll la fonction header_scroll() est exectuée
//window.addEventListener("scroll", function(){header_scroll()});
window.onscroll = header_scroll;

function header_scroll(){
	const elem = document.getElementsByClassName("header-menu")[0];
	const entete = document.getElementsByClassName("header-contenu")[0];
	if(elem.getBoundingClientRect().top < 0 && !elem.classList.contains('header-fixe')){
		elem.classList.add('header-fixe');
		entete.style.paddingBottom = "70px";
	} else if(entete.getBoundingClientRect().bottom > 70 && elem.classList.contains('header-fixe')){
	elem.classList.remove('header-fixe');
	entete.style.paddingBottom = "10px";
	}
}
