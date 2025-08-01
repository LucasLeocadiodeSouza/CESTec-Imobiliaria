/* 
   Dev: Lucas Leocadio de Souza
   Data: 31/05/25
   IM: 00020

    consulForm_init -> funcao para importar a criacao do objeto consulta, necessario importar: import { consulForm_init }   from (caminho, ex: "../modules/consulForm.js");

    Objeto CONSUL->
        CONSUL = new consulForm_init(); ** inicializar o metodo de consulta;

        CONSUL.consultar 
                         Faz um fetch busca no path passado no parametro e retorna o resultado no metodo interno filaFetch;

                         Recebe 5 parametros:
                         nomefuncao -> Tipo String. Nome da funcao para organizar a fila de multiplas chamadas. Parametro obrgatório, necessario informar para realizar o retorno de algum resultado esperado no filaFetch;
                         path       -> Tipo String. Caminho para o metodo esperado, parametro obrigatorio;
                         method     -> Tipo String. Atribui o tipo da chamada de fetch, GET, POST..., por padrao é atribuido como um "GET";
                         headers    -> Tipo JSON. Atribui o header para o fetch. Parametro nao obrigatorio, por padrao é atribuido como { "Content-Type": "application/json" };
                         options    -> Tipo JSON. Atribui o body para a chamada que é necessario usar o body ou um responseType diferente como 'arraybuffer' (options.responseType === 'arraybuffer'), Parametro nao obrigatorio. Ex: options.responseType === 'arraybuffer' ou options.body = {...};
*/

function consulForm_init(){
    this.obj = "";

    let loaderTimeout;

    let loader = document.createElement('div');
    loader.id    = 'global-loader';
    loader.style = `position: fixed;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    background: rgb(255 255 255 / 55%);
                    z-index: 9999;
                    display: none;`;

    const spinner = document.createElement('img');
    spinner.src   = '/icons/loader_icon.png';
    spinner.style = `position: absolute;
                    top: 50%;
                    left: 50%;
                    transform: translate(-50%, -50%);
                    animation: girar 1s linear infinite;`;

    loader.appendChild(spinner);

    const style = document.createElement('style');
    style.innerHTML = `
        @keyframes girar {
            from { transform: translate(-50%, -50%) rotate(0deg); }
            to { transform: translate(-50%, -50%) rotate(360deg); }
        }
    `;

    this.consultar = async (nomefuncao,path,method,headers,options = {},ocultarloader)=>{
        if(!path) throw new Error("Caminho não especificado");
    
        if(!method) method = "GET";
        if(!headers) headers = { "Content-Type": "application/json" };

        if(!ocultarloader){
            document.head.appendChild(style);

            if(!document.body.contains(loader)) document.body.appendChild(loader);

            loader.style.display = 'block';
            loaderTimeout = setTimeout(() => {
                loader.innerHTML += '<p>Carregando...</p>';
            }, 1000); // Mensagem após 1 segundos
        }

           const response = await fetch(path, {
                method: method,
                headers: headers,
                body: method !== 'GET' ? JSON.stringify(options.body) : null
            });
            
            var resulta;

            this.obj = nomefuncao;
            
            if (!response.ok) {
                if(!ocultarloader){
                    clearTimeout(loaderTimeout);
                    loader.remove();
                }

                this.filaFetch(`Erro na requisição: ${response.status} - ${response.statusText}`, response.status);

                throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
            }

            const contentType = response.headers.get('Content-Type');
            

            if (contentType?.includes('application/pdf') || options.responseType === 'arraybuffer') {
                resulta = await response.arrayBuffer();
            }
            else if (contentType?.includes('application/json')) {
                resulta = await response.json();
            } else {
                resulta = await response.text();
            }

            this.filaFetch(resulta);

            if(!ocultarloader){
                clearTimeout(loaderTimeout);
                loader.remove();
            }
            
            return resulta;
    };
}