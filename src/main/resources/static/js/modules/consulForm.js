/* 
   Dev: Lucas Leocadio de Souza
   Data: 31/05/25
   IM: 00020
*/

export function consulForm_init(){
    const loader = document.createElement('div');
    loader.id = 'global-loader';
    loader.style = `position: fixed;
                    top: 0;
                    left: 0;
                    width: 100%;
                    height: 100%;
                    background: rgba(0,0,0,0.7) url('/icons/loader_icon.png') no-repeat center;
                    z-index: 9999;
                    display: none;`;

    document.body.appendChild(loader);

    this.consultar = async (path,method,headers,options = {})=>{
         if(!path) throw new Error("Caminho não especificado");
    
        if(!method) method = "GET";
        if(!headers) headers = { "Content-Type": "application/json" };

        loader.style.display = 'block';
        let loaderTimeout = setTimeout(() => {
            loader.innerHTML += '<p>Carregando...</p>';
        }, 3000); // Mensagem após 3 segundos

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

            if (contentType?.includes('application/pdf') || options.responseType === 'arraybuffer') {
                return await response.arrayBuffer();
            }
            else if (contentType?.includes('application/json')) {
                return await response.json();
            } else {
                return await response.text();
            }
        } catch (error) {
            throw new Error('Erro na consulta:', error);
        } finally {
            clearTimeout(loaderTimeout);
            loader.style.display = 'none';
        }
    };
}