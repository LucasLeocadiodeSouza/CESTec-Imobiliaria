/* 
   Dev: Lucas Leocadio de Souza
   Data: 31/05/25
   IM: 00020
*/

export function consulForm_init(){
    this.consultar = async (path,method,headers,body)=>{
         if(!path) throw new Error("Caminho não especificado");
    
        if(!method) method = "GET";
        if(!headers) headers = { "Content-Type": "application/json" };

        try {
           const response = await fetch(path, {
                method: method,
                headers: headers,
                body: method !== 'GET' ? JSON.stringify(body) : null
            });

            if (!response.ok) {
                throw new Error(`Erro na requisição: ${response.status} - ${response.statusText}`);
            }

            const contentType = response.headers.get('content-type');
            if (contentType?.includes('application/json')) {
                return await response.json();
            } else {
                return await response.text();
            }
        } catch (error) {
            throw new Error('Erro na consulta:', error);
        }
    };
}