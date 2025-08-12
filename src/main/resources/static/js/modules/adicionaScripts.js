function adicionarHeadLinks(){
    const favicon = document.createElement("link");
    favicon.rel   = 'icon';
    favicon.href  = "/icons/iconLogoEmpresa.png";
    favicon.type  = "image/x-icon";

    const link1 = document.createElement("link");
    link1.rel   = 'preconnect';
    link1.href  = "https://fonts.googleapis.com";

    const link2 = document.createElement("link");
    link2.rel   = 'preconnect';
    link2.href  = "https://fonts.gstatic.com";
    link2.crossOrigin;

    const linkFontLato = document.createElement("link");
    linkFontLato.rel   = 'stylesheet';
    linkFontLato.href  = "https://fonts.googleapis.com/css2?family=Bentham&family=Host+Grotesk:ital,wght@0,300..800;1,300..800&family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap";

    const linkFontRoboto = document.createElement("link");
    linkFontRoboto.rel   = 'stylesheet';
    linkFontRoboto.href  = "https://fonts.googleapis.com/css2?family=Host+Grotesk:ital,wght@0,300..800;1,300..800&family=Monomakh&family=Roboto+Slab:wght@100..900&display=swap";

    const linkFontInter  = document.createElement("link");
    linkFontInter.rel    = 'stylesheet';
    linkFontInter.href   = "https://fonts.googleapis.com/css2?family=Bentham&family=Host+Grotesk:ital,wght@0,300..800;1,300..800&family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&family=Montserrat:ital,wght@0,100..900;1,100..900&display=swap";

    const linkFontRubik  = document.createElement("link");
    linkFontRubik.rel    = 'stylesheet';
    linkFontRubik.href   = "https://fonts.googleapis.com/css2?family=Arimo:ital,wght@0,400..700;1,400..700&family=Bentham&family=Host+Grotesk:ital,wght@0,300..800;1,300..800&family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&family=Lato:ital,wght@0,100;0,300;0,400;0,700;0,900;1,100;1,300;1,400;1,700;1,900&family=Montserrat:ital,wght@0,100..900;1,100..900&family=Rubik:ital,wght@0,300..900;1,300..900&display=swap";

    document.head.appendChild(favicon);
    document.head.appendChild(link1);
    document.head.appendChild(link2);
    document.head.appendChild(linkFontLato);
    document.head.appendChild(linkFontRoboto);
    document.head.appendChild(linkFontInter);
    document.head.appendChild(linkFontRubik);
}

function addScript(prog){
    const script = document.createElement("script");
    script.src   = prog;
    script.defer;

    document.head.appendChild(script);
}

function addScriptsHead(progs){
    const programasPadrao = '/js/modules/gridForm.js,/js/modules/dmfForm.js,/js/modules/abaForm.js,/js/modules/consulForm.js,/js/modules/elementsForm.js,/js/modules/utils.js,/js/modules/zoomForm.js';
    const programasHead   = programasPadrao + "," + progs;

    programasHead.split(",").forEach(prog => {
        addScript(prog);
    });
}

window.adicionarHeadLinks = adicionarHeadLinks;
window.addScriptsHead     = addScriptsHead;