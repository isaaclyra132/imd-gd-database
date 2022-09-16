select pname from
(select distinct pno, pjno, pname from parts, projects, sppj
where pno = parts_pno and pjno = projects_pjno
group by pno, pjno) as s01
group by pno
having count(*) = 2