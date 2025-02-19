
insert into funcionario value(null, 1,3000,"Lucas Leocadio de Souza","2025-02-16","6783256745","5178438922","Joaquim Nabuco, 23");
insert into funcionario value(null, 1,4400,"Pedro Henrique","2025-01-13","645465446","435654466","Europa Nabuco, 222");
insert into funcionario value(null, 1,2700,"Joaquim Souza","2022-08-16","1303826483","0288562868","Aurora, 12");
insert into funcionario value(null, 1,3000,"Gabriel Gustavo","2025-12-24","2489244666","57474645600","Requiao, 3");

insert into pcp_corretor value(null, "6783256745","3490377864","lucas@gmail.com",1);
insert into pcp_corretor value(null, "2002842334","5178438922","gabriel@gmail.com",4);
insert into pcp_corretor value(null, "1303826483","0288562868","joaquim@gmail.com",3);
insert into pcp_corretor value(null, "2489244666","3822918345","pedro@gmail.com",2);

show tables;

SELECT * FROM pcp_cliente;
SELECT * FROM pcp_proprietario;
SELECT * FROM pcp_corretor;
SELECT * FROM funcionario;
SELECT * FROM pcp_venda;

desc pcp_comissao