/* 
   Dev: Lucas Leocadio de Souza
   Data: 22/02/25
   IM: 00009
*/

export function createGrid(id,column,width,dados){
    const table     = document.getElementById(id);
    const thead     = document.createElement("thead");
    const headerRow = document.createElement("tr");

    const colunas = column.split(",");
    colunas.forEach((coluna,index) => {
        const th = document.createElement("th");
        th.textContent =  coluna.trim();
        headerRow.appendChild(th);
    });
    thead.appendChild(headerRow);
    table.appendChild(thead);

    const tbody = document.createElement("tbody");
    tbody.setAttribute("id", `${id}-tbody`);
    table.appendChild(tbody);

    dados.forEach(dado => {
        const row = document.createElement("tr");        
        Object.values(dado).forEach(valor => {
            const td = document.createElement("td");
            td.textContent = valor;
            row.appendChild(td);
        });    
        tbody.appendChild(row);
    });

    return table;
}