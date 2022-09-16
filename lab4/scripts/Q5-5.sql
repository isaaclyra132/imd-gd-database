select distinct 
	s.name from students s , enrolls e , courses c, bookuses bu, (select * from books where publisher != 'Addison Wesley' or author != 'Navathe') as b
where students_ssn=ssn and e.courses_cnum=cnum and c.cnum = bu.courses_cnum and bu.books_isbn = isbn