/* 
    Dev: Lucas Leocadio de Souza
    Data: 22/02/25
    Descricao: Arquivo de metodos gerais
*/

function form(obj){
    return document.getElementById(obj);
}

export function TableGrid(){
    this.id          = "";
    this.column      = "";
    this.columnName  = "";
    this.columnWidth = "";
    this.dados       = [];

    this.createTable = function(){
                            form(this.id).innerText = '';
                            const table     = form(this.id);
                            const thead     = document.createElement("thead");
                            const headerRow = document.createElement("tr");
                            const colunas   = this.column.split(",");
                        
                            var pi = 0;
                            colunas.forEach((coluna,index) => {
                                const th = document.createElement("th");
                                th.id = coluna.trim() + "__" + pi;        
                                th.textContent =  coluna.trim();
                                headerRow.appendChild(th);
                                pi += 1;
                            });
                            thead.appendChild(headerRow);
                            table.appendChild(thead);
                        
                            const tbody = document.createElement("tbody");
                            tbody.setAttribute("id", `${this.id}-tbody`);
                            table.appendChild(tbody);
                        
                            var pi = 0;
                            colunas.forEach((coluna, index)=>{
                                form(coluna.trim() + "__" + pi).innerText   = this.columnName.split(",")[pi];
                                form(coluna.trim() + "__" + pi).style.width = this.columnWidth.split(",")[pi] +"%"; 
                                pi += 1;
                            });
                            return table;
                        }

    this.carregaDadosGrid = function(){
                                let colunas = form(this.id).children[0].children[0].children;

                                for(var i = 0; i < dados.length; i++){
                                    const row = document.createElement("tr");

                                    for(var j = 0; j < colunas.length; j++){
                                        const td  = document.createElement("td");
                                        var valor = dados[i][colunas[j].id.replace("__" + j ,"").trim()];
                                        td.textContent = valor? valor :"N/A";
                                        row.appendChild(td);
                                    }
                            
                                    form(this.id).appendChild(row);
                                }
                                return "";
                            }
    
}