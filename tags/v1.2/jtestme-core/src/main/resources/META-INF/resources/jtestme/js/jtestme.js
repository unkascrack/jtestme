function showHide(id) {
	if (document.getElementById(id).style.display == 'none') {
		if (document.getElementById(id + 'Img') != null) {
			document.getElementById(id + 'Img').src = '?resource=img/minus.png';
		}
		document.getElementById(id).style.display = 'inline';
	} else {
		if (document.getElementById(id + 'Img') != null) {
			document.getElementById(id + 'Img').src = '?resource=img/plus.png';
		}
		document.getElementById(id).style.display = 'none';
	}
}