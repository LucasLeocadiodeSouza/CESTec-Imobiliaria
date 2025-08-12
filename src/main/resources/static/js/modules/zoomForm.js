/* 
   Dev: Lucas Leocadio de Souza
   Data: 06/08/25
   IM: 00026

   ZOOM -> link para um filtro sobre algum dado;

*/

function zoom_init(path, columnGrid, columnLabel, columnWidth, columnAlign, parametros){
    var ZOOM_GRID;

   elementsForm_init();

   ZOOM_GRID               = new GridForm_init();
   ZOOM_GRID.id            = "table_zoom";
   ZOOM_GRID.columnName    = columnGrid;
   ZOOM_GRID.columnLabel   = columnLabel;
   ZOOM_GRID.columnWidth   = columnWidth;
   ZOOM_GRID.columnAlign   = columnAlign;
   ZOOM_GRID.mousehouve    = true;
   ZOOM_GRID.createGrid();

    CONSUL = new consulForm_init();
    
    event_click_table();

    carregarGridZoom(query, parametros);

    function event_click_table(obj, row){
        switch (obj) {
        case ZOOM_GRID: const valoresLinha = ZOOM_GRID.getRowNode(row);
                        console.log("valor " + valoresLinha[0]);
                        break;
        };
    }

    function carregarGridZoom(query, parametros){
        const request = {query : query,
                        parametros : parametros};

        ZOOM_GRID.carregaGrid(path,"","", true, request);
    }
}