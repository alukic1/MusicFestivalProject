ALTER TABLE `festivaldb`.`vrsta_ulaznice` 
DROP COLUMN `MaxBrojUlaznica`,
DROP COLUMN `Cijena`;


INSERT INTO `festivaldb`.`izvodjac` (`IdIzvodjaca`) VALUES ('1');
INSERT INTO `festivaldb`.`izvodjac` (`IdIzvodjaca`) VALUES ('2');
INSERT INTO `festivaldb`.`izvodjac` (`IdIzvodjaca`) VALUES ('3');
INSERT INTO `festivaldb`.`izvodjac` (`IdIzvodjaca`) VALUES ('4');
INSERT INTO `festivaldb`.`izvodjac` (`IdIzvodjaca`) VALUES ('5');
INSERT INTO `festivaldb`.`izvodjac` (`IdIzvodjaca`) VALUES ('6');


INSERT INTO `festivaldb`.`muzicar` (`IdIzvodjaca`, `Ime`, `Prezime`) VALUES ('1', 'Dejan', 'Cukic');
INSERT INTO `festivaldb`.`muzicar` (`IdIzvodjaca`, `Ime`, `Prezime`) VALUES ('3', 'Mile', 'Kekin');

INSERT INTO `festivaldb`.`bend` (`IdIzvodjaca`, `Naziv`) VALUES ('2', 'Riblja corba');
INSERT INTO `festivaldb`.`bend` (`IdIzvodjaca`, `Naziv`) VALUES ('4', 'Elektricni orgazam');
INSERT INTO `festivaldb`.`bend` (`IdIzvodjaca`, `Naziv`) VALUES ('5', 'Divlje jagode');
INSERT INTO `festivaldb`.`bend` (`IdIzvodjaca`, `Naziv`) VALUES ('6', 'Mortal Kombat');


INSERT INTO `festivaldb`.`drzava` (`Naziv`) VALUES ('BiH');
INSERT INTO `festivaldb`.`drzava` (`Naziv`) VALUES ('Srbija');

INSERT INTO `festivaldb`.`grad` (`PostanskiBroj`, `Naziv`, `IdDrzave`) VALUES ('78000', 'Banja Luka', '1');
INSERT INTO `festivaldb`.`grad` (`PostanskiBroj`, `Naziv`, `IdDrzave`) VALUES ('400107', 'Novi Sad', '2');

INSERT INTO `festivaldb`.`kalendar` (`IdGrada`, `DatumOd`, `DatumDo`) VALUES ('1', '2024/05/31', '2024/06/01');
INSERT INTO `festivaldb`.`kalendar` (`IdGrada`, `DatumOd`, `DatumDo`) VALUES ('2', '2024/07/10', '2024/07/14');

INSERT INTO `festivaldb`.`vrsta_ulaznice` (`Naziv`) VALUES ('Regular');
INSERT INTO `festivaldb`.`vrsta_ulaznice` (`Naziv`) VALUES ('VIP');
INSERT INTO `festivaldb`.`vrsta_ulaznice` (`Naziv`) VALUES ('VIP Plus');


INSERT INTO `festivaldb`.`festival_vrsta_ulaznice` (`FESTIVAL_IdFestivala`, `VRSTA_ULAZNICE_IdVrsteUlaznice`, `Cijena`, `MaxBrojUlaznica`) VALUES ('8', '1', '15', '10');
INSERT INTO `festivaldb`.`festival_vrsta_ulaznice` (`FESTIVAL_IdFestivala`, `VRSTA_ULAZNICE_IdVrsteUlaznice`, `Cijena`, `MaxBrojUlaznica`) VALUES ('8', '2', '30', '300');
INSERT INTO `festivaldb`.`festival_vrsta_ulaznice` (`FESTIVAL_IdFestivala`, `VRSTA_ULAZNICE_IdVrsteUlaznice`, `Cijena`, `MaxBrojUlaznica`) VALUES ('8', '3', '50', '200');
