select sname from
(select distinct pno, pjno, sname from parts, (select * from projects where pjno ='J1') as s01, suppliers, sppj
where pno = parts_pno and pjno = projects_pjno and sno = suppliers_sno) as s02
group by sname
having count(*) > 2