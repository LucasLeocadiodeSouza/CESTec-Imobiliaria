/* 
    Dev: Lucas Leocadio de Souza
    Data: 22/03/25
    IM: 00008
*/
window.addEventListener("load", function () {
    iniciarEventos();
    buscarUserName();
});

var ABA,DMFDiv,CONSUL,CONTRATOS_GRID;

function iniciarEventos() {
    elementsForm_init();

    CONTRATOS_GRID               = new GridForm_init();
    CONTRATOS_GRID.id            = "tabela_aprovacao";
    CONTRATOS_GRID.columnName    = "codcontrato,codimovel,tipo,codproprietario,nomeProp,codcliente,nomeCliente,codcorretor,nomeCorretor,periodo,preco,valor,vlrcondominio,area,quartos,documento,endereco,vlrliber,observacao";
    CONTRATOS_GRID.columnLabel   = "Cód. Contrato,Cód. Imovel,Tipo Imovel,Código Prop,Proprietario,Código Cliente,Cliente,Código Corretor,Corretor,Periodo,Preço (R$),Valor negoc. (R$)";
    CONTRATOS_GRID.columnWidth   = "7,6,9,6,11,6,11,6,11,11,8,8";
    CONTRATOS_GRID.columnAlign   = "c,c,e,c,e,c,e,c,e,c,d,d";
    CONTRATOS_GRID.mousehouve    = true;
    CONTRATOS_GRID.destacarclick = true;
    CONTRATOS_GRID.gridWidth     = "2200px";
    CONTRATOS_GRID.gridHeight    = "auto";
    CONTRATOS_GRID.createGrid();

    ABA      = new abaForm_init();
    ABA.id   = "abas";
    ABA.name = "Consulta,Analise Contrato";
    ABA.icon = "/icons/consultaLupa.png,/icons/manutencaoIcon.png";
    ABA.createAba();

    DMFDiv              = new DMFForm_init();
    DMFDiv.divs         = "dmodalf_aprovacao";
    DMFDiv.tema         = 1;
    DMFDiv.cortinaclose = true;
    DMFDiv.formModal();

    CONSUL = new consulForm_init();
    filaFetchInit()

    controlaTela("inicia");

    event_click("bnovabusca");
    event_click("bbuscar");
    event_click("blimpar");
    event_click("baprovar");
    event_click("breprovar");
    
    event_blur_init("mvlrlib");
    event_selected_init("mvlrlib,mobs,codproprietario,codcliente,codcorretor");
    inputOnlyNumber('codcliente,codproprietario,mvlrlib,codcorretor');

    CONSUL.filterChange('codproprietario','',`/gen/getNomeProp`,['codprop:codproprietario'],'descproprietario');
    CONSUL.filterChange('codcliente','',`/gen/getNomeCliente`,['codcli:codcliente'],'desccliente');
    CONSUL.filterChange('codcorretor','',`/gen/getNomeByCodFunc`,['codfunc:codcorretor'],'desccorretor');
}


function event_click(obj) {
    form(obj).addEventListener("click", ()=>{
        switch (obj) {
            case "bnovabusca": controlaTela("novabusca");
                               break;

            case    "blimpar": controlaTela("inicia");
                               break;

            case    "bbuscar": controlaTela("buscar");
                               buscarContratoAprovacao();
                               break;

            case   "baprovar": if(!confirm("Deseja mesmo Aprovar esse contrato?")) return;
                               aprovarReprovarContrato(2);
                               break;

            case  "breprovar": if(!confirm("Deseja mesmo Reprovar esse contrato?")) return;
                               aprovarReprovarContrato(3);
                               break;
        }
    });
}

function event_click_table(obj,row){
    switch (obj) {
    case CONTRATOS_GRID: const valoresLinha = CONTRATOS_GRID.getRowNode(row);
                         form("sacao").innerText   = ehConsulta()?"Consultando":"Analisando";
                         form("stitulo").innerText = form("sacao").innerText + " o Contrato - " + form("sacao").innerText;
                         controlaTela("modal");

                         puxarFichaContrato(valoresLinha);
        
                         form("hcodcorretor").value = valoresLinha[0];
                         form('mvlrlib').value      = valoresLinha[17];
                         form('mobs').value         = valoresLinha[18];

                         DMFDiv.openModal("dmodalf_aprovacao");
                         break;
    }
}

function event_click_aba(){
    switch (ABA.getIndex()) {
    case 0: 
    case 1: controlaTela("inicia");
            break;
    }
}

function filaFetchInit(){
    CONSUL.filaFetch = (retorno)=>{
        switch (CONSUL.obj) {
        case             "buscarUserName": form("ideusu").value = retorno;
                                           break;

        case    "aprovarReprovarContrato": alert("Sucesso!", "Contrato " + (retorno == 2?"Aprovado":"Reprovado") + " com Sucesso!",4);
                                           enviarEmailAprovacaoReprovacao(retorno);

                                           DMFDiv.closeModal();

                                           form("bnovabusca").click();
                                           form("bbuscar").click();
                                           break;
        }
    }
}

function controlaTela(opc){
    limparTela(opc);
    if(opc == "inicia" || opc == 'novabusca'){
        desabilitaCampo('bnovabusca',        true);
        desabilitaCampo('bbuscar',           false);
        desabilitaCampo('codproprietario',   false);
        desabilitaCampo('codcliente',        false);
        desabilitaCampo('codcorretor',       false);
    }
    if(opc == "buscar"){
        desabilitaCampo('bnovabusca',       false);
        desabilitaCampo('bbuscar',          true);
        desabilitaCampo('codproprietario',  true);
        desabilitaCampo('codcliente',       true);
        desabilitaCampo('codcorretor',      true);
    }
    if(opc == "modal"){
        desabilitaCampo('mvlrlib', ehConsulta());
        desabilitaCampo('mobs',    ehConsulta());

        setDisplay("baprovar",  ehManutencao()?"flex":"none");
        setDisplay("breprovar", ehManutencao()?"flex":"none");
    }
}


function limparTela(opc){
    if(opc == "inicia" || opc == 'novabusca'){
        form('codproprietario').value  = "0";
        form('descproprietario').value = "Todos os Proprietarios";
        form('codcliente').value       = "0";
        form('desccliente').value      = "Todos os Clientes";
        form('codcorretor').value      = "0";
        form('desccorretor').value     = "Todos os Corretores";
        setRadioValue('rimovel','0'); 
    }
    if(opc === "inicia" || opc === "novabusca"){
        CONTRATOS_GRID.clearGrid();
    }
    if(opc === "modal"){
        form("hcodcorretor").value = "";
        form('mvlrlib').value      = "0";
        form('mobs').value         = "";
    }
} 

function ehConsulta(){
    return ABA.getIndex() === 0;
}

function ehManutencao(){
    return ABA.getIndex() === 1;
}

function puxarFichaContrato(valoresLinha){
    form('codimovel').innerText       = valoresLinha[1];
    form('tpimovel').innerText        = valoresLinha[2];
    form('vlrimovel').innerText       = valoresLinha[10];
    form('vlrcondominio').innerText   = valoresLinha[12];
    form('areatotal').innerText       = valoresLinha[13];
    form('numquartos').innerText      = valoresLinha[14];
    form('endereco').innerText        = valoresLinha[16];
    form('codcontrato').innerText     = valoresLinha[0];
    form('tpcontrato').innerText      = valoresLinha[1];
    form('vlrcontrato').innerText     = valoresLinha[11];
    form('periodocontrato').innerText = valoresLinha[9];
    form('corretor').innerText        = valoresLinha[7] + " - " + valoresLinha[8];
    form('codnegociante').innerText   = valoresLinha[5];
    form('nomecliente').innerText     = valoresLinha[6];
    form('cpfcliente').innerText      = valoresLinha[15];
}

function buscarContratoAprovacao(){
    CONTRATOS_GRID.carregaGrid(`/cri005/buscarContratoAprovacao`,["codprop:codproprietario",
                                                                  "codcliente:codcliente",
                                                                  "codcorretor:codcorretor",
                                                                  "tipimovel=" + getRadioValue("rimovel"),
                                                                  "acao=" + ABA.getIndex()]);
}

function enviarEmailAprovacaoReprovacao(acao){
    const email = {
        to: form("memail").value,
        subject: "Contrato " + (acao == 2?"Aprovado":"Reprovado") + "!",
        body: "<html>"
            + "<head>"
            + "</head>"
            + "<body style='font-family: Arial, sans-serif; color: #333;'>"
            + '<div style="max-width: 600px; margin: auto; padding: 20px; border: 1px solid #e0e0e0; border-radius: 8px;">'
            + "<p>Após análise o contrato foi " + (acao == 2?"Aprovado":"Reprovado") + " pelo gestor!</p>"
            + "<p><strong>Código do contrato:</strong>" + form("hcodcorretor").value + "</p>"
            + (acao == 1?"<p><strong>Valor Liberado:</strong> " + form("mvlrlib").value + "</p>":"")
            + "<p><strong>Observacão:</strong> " + form("mobs").value + "</p>"
            + "</div>"
            + "</body>"
            + "</html>"}

    CONSUL.consultar("enviarEmailAprovacaoReprovacao",`/email`,[],"POST",{ "Content-Type": "application/json" },{body: email})
}

function aprovarReprovarContrato(acao) {
    CONSUL.consultar("aprovarReprovarContrato",`/cri005/aprovarReprovarContrato`,["codcontrato:hcodcorretor",
                                                                                   "valorliberado:mvlrlib",
                                                                                   "observacao:mobs",
                                                                                   "situacao=" + acao],
                                                                                   "POST");
}

function buscarUserName(){
    CONSUL.consultar("buscarUserName",`/home/userlogin`)
}