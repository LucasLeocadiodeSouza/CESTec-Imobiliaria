/* 
   Dev: Lucas Leocadio de Souza
   Data: 31/05/25
   IM: 00020
*/

export function consulForm_init(){
    this.obj = "";

    const loader = document.createElement('div');
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
    document.head.appendChild(style);

    document.body.appendChild(loader);

    this.consultar = async (nomefuncao,path,method,headers,options = {})=>{
         if(!path) throw new Error("Caminho não especificado");
    
        if(!method) method = "GET";
        if(!headers) headers = { "Content-Type": "application/json" };

        loader.style.display = 'block';
        let loaderTimeout = setTimeout(() => {
            loader.innerHTML += '<p>Carregando...</p>';
        }, 1000); // Mensagem após 1 segundos

        try {
           const response = await fetch(path, {
                method: method,
                headers: headers,
                body: method !== 'GET' ? JSON.stringify(options.body) : null
            });

            if (!response.ok) {
                throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
            }

            const contentType = response.headers.get('Content-Type');
            var resulta;

            if (contentType?.includes('application/pdf') || options.responseType === 'arraybuffer') {
                resulta = await response.arrayBuffer();
            }
            else if (contentType?.includes('application/json')) {
                resulta = await response.json();
            } else {
                resulta = await response.text();
            }

            this.obj = nomefuncao;

            this.filaFetch(resulta);

            return resulta;
        } catch (error) {
            throw new Error('Erro na consulta:', error);
        } finally {
            clearTimeout(loaderTimeout);
            loader.style.display = 'none';
        }
    };
}