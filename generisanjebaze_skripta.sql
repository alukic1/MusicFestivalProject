-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- -----------------------------------------------------
-- Schema festivaldb
-- -----------------------------------------------------

-- -----------------------------------------------------
-- Schema festivaldb
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `festivaldb` DEFAULT CHARACTER SET utf8mb3 ;
USE `festivaldb` ;

-- -----------------------------------------------------
-- Table `festivaldb`.`administrator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`administrator` (
  `KorisnickoIme` VARCHAR(20) NOT NULL,
  `Lozinka` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`KorisnickoIme`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`izvodjac`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`izvodjac` (
  `IdIzvodjaca` INT NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IdIzvodjaca`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`bend`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`bend` (
  `IdIzvodjaca` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(60) NOT NULL,
  PRIMARY KEY (`IdIzvodjaca`),
  CONSTRAINT `FK_Bend_IdIzvodjaca`
    FOREIGN KEY (`IdIzvodjaca`)
    REFERENCES `festivaldb`.`izvodjac` (`IdIzvodjaca`))
ENGINE = InnoDB
AUTO_INCREMENT = 7
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`drzava`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`drzava` (
  `IdDrzave` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(100) NOT NULL,
  PRIMARY KEY (`IdDrzave`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`grad`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`grad` (
  `IdGrada` INT NOT NULL AUTO_INCREMENT,
  `PostanskiBroj` VARCHAR(10) NULL DEFAULT NULL,
  `Naziv` VARCHAR(200) NOT NULL,
  `IdDrzave` INT NULL DEFAULT NULL,
  PRIMARY KEY (`IdGrada`),
  INDEX `IdDrzave_idx` (`IdDrzave` ASC) VISIBLE,
  CONSTRAINT `FK_Grad_IdDrzave`
    FOREIGN KEY (`IdDrzave`)
    REFERENCES `festivaldb`.`drzava` (`IdDrzave`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`kalendar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`kalendar` (
  `IdKalendara` INT NOT NULL AUTO_INCREMENT,
  `IdGrada` INT NULL DEFAULT NULL,
  `DatumOd` DATE NOT NULL,
  `DatumDo` DATE NOT NULL,
  PRIMARY KEY (`IdKalendara`),
  INDEX `IdGrada_idx` (`IdGrada` ASC) VISIBLE,
  CONSTRAINT `FK_Kalendar_IdGrada`
    FOREIGN KEY (`IdGrada`)
    REFERENCES `festivaldb`.`grad` (`IdGrada`))
ENGINE = InnoDB
AUTO_INCREMENT = 3
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`festival`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`festival` (
  `IdFestivala` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(100) NOT NULL,
  `Opis` TEXT NULL DEFAULT NULL,
  `IdKalendara` INT NULL DEFAULT NULL,
  `Email` VARCHAR(256) NULL DEFAULT NULL,
  `Exists` TINYINT NOT NULL DEFAULT '1',
  PRIMARY KEY (`IdFestivala`),
  INDEX `IdKalendara_idx` (`IdKalendara` ASC) VISIBLE,
  CONSTRAINT `FK_Festival_IdKalendara`
    FOREIGN KEY (`IdKalendara`)
    REFERENCES `festivaldb`.`kalendar` (`IdKalendara`))
ENGINE = InnoDB
AUTO_INCREMENT = 8
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`bina`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`bina` (
  `IdBine` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  `Oznaka` VARCHAR(45) NOT NULL,
  `IdFestivala` INT NULL DEFAULT NULL,
  `Exists` TINYINT NOT NULL DEFAULT '1',
  PRIMARY KEY (`IdBine`),
  INDEX `IdFestivala_idx` (`IdFestivala` ASC) VISIBLE,
  CONSTRAINT `FK_Bina_IdFestivala`
    FOREIGN KEY (`IdFestivala`)
    REFERENCES `festivaldb`.`festival` (`IdFestivala`))
ENGINE = InnoDB
AUTO_INCREMENT = 9
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`vrsta_ulaznice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`vrsta_ulaznice` (
  `IdVrsteUlaznice` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  `Cijena` DECIMAL(5,0) NOT NULL,
  `MaxBrojUlaznica` INT NOT NULL,
  PRIMARY KEY (`IdVrsteUlaznice`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`festival_vrsta_ulaznice`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`festival_vrsta_ulaznice` (
  `FESTIVAL_IdFestivala` INT NOT NULL,
  `VRSTA_ULAZNICE_IdVrsteUlaznice` INT NOT NULL,
  `Cijena` DECIMAL(5,2) NULL DEFAULT NULL,
  `MaxBrojUlaznica` INT NULL DEFAULT NULL,
  PRIMARY KEY (`FESTIVAL_IdFestivala`, `VRSTA_ULAZNICE_IdVrsteUlaznice`),
  INDEX `fk_FESTIVAL_has_VRSTA_ULAZNICE_VRSTA_ULAZNICE1_idx` (`VRSTA_ULAZNICE_IdVrsteUlaznice` ASC) VISIBLE,
  INDEX `fk_FESTIVAL_has_VRSTA_ULAZNICE_FESTIVAL1_idx` (`FESTIVAL_IdFestivala` ASC) VISIBLE,
  CONSTRAINT `fk_FESTIVAL_has_VRSTA_ULAZNICE_FESTIVAL1`
    FOREIGN KEY (`FESTIVAL_IdFestivala`)
    REFERENCES `festivaldb`.`festival` (`IdFestivala`),
  CONSTRAINT `fk_FESTIVAL_has_VRSTA_ULAZNICE_VRSTA_ULAZNICE1`
    FOREIGN KEY (`VRSTA_ULAZNICE_IdVrsteUlaznice`)
    REFERENCES `festivaldb`.`vrsta_ulaznice` (`IdVrsteUlaznice`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`muzicar`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`muzicar` (
  `IdIzvodjaca` INT NOT NULL AUTO_INCREMENT,
  `Ime` VARCHAR(45) NOT NULL,
  `Prezime` VARCHAR(45) NOT NULL,
  `Nadimak` VARCHAR(45) NULL DEFAULT '" "',
  PRIMARY KEY (`IdIzvodjaca`),
  CONSTRAINT `FK_Muzicar_IdIzvodjaca`
    FOREIGN KEY (`IdIzvodjaca`)
    REFERENCES `festivaldb`.`izvodjac` (`IdIzvodjaca`))
ENGINE = InnoDB
AUTO_INCREMENT = 4
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`muzicar_u_bendu`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`muzicar_u_bendu` (
  `MUZICAR_IdIzvodjaca` INT NOT NULL,
  `BEND_IdIzvodjaca` INT NOT NULL,
  PRIMARY KEY (`MUZICAR_IdIzvodjaca`, `BEND_IdIzvodjaca`),
  INDEX `fk_MUZICAR_has_BEND_BEND1_idx` (`BEND_IdIzvodjaca` ASC) VISIBLE,
  INDEX `fk_MUZICAR_has_BEND_MUZICAR1_idx` (`MUZICAR_IdIzvodjaca` ASC) VISIBLE,
  CONSTRAINT `fk_MUZICAR_has_BEND_BEND1`
    FOREIGN KEY (`BEND_IdIzvodjaca`)
    REFERENCES `festivaldb`.`bend` (`IdIzvodjaca`),
  CONSTRAINT `fk_MUZICAR_has_BEND_MUZICAR1`
    FOREIGN KEY (`MUZICAR_IdIzvodjaca`)
    REFERENCES `festivaldb`.`muzicar` (`IdIzvodjaca`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`nastup`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`nastup` (
  `IdIzvodjaca` INT NOT NULL,
  `IdBine` INT NOT NULL,
  `VrijemeNastupa` DATETIME(5) NOT NULL,
  `Exists` TINYINT NOT NULL DEFAULT '1',
  PRIMARY KEY (`IdIzvodjaca`, `IdBine`),
  INDEX `IdIzvodjaca_idx` (`IdIzvodjaca` ASC) VISIBLE,
  INDEX `IdBine_idx` (`IdBine` ASC) VISIBLE,
  CONSTRAINT `FK_Nastup_IdBine`
    FOREIGN KEY (`IdBine`)
    REFERENCES `festivaldb`.`bina` (`IdBine`),
  CONSTRAINT `FK_Nastup_IdIzvodjaca`
    FOREIGN KEY (`IdIzvodjaca`)
    REFERENCES `festivaldb`.`izvodjac` (`IdIzvodjaca`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`organizator`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`organizator` (
  `IdOrganizatora` INT NOT NULL,
  `Ime` VARCHAR(45) NOT NULL,
  `Prezime` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IdOrganizatora`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`organizator_festival`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`organizator_festival` (
  `ORGANIZATOR_IdOrganizatora` INT NOT NULL,
  `FESTIVAL_IdFestivala` INT NOT NULL,
  PRIMARY KEY (`ORGANIZATOR_IdOrganizatora`, `FESTIVAL_IdFestivala`),
  INDEX `fk_ORGANIZATOR_has_FESTIVAL_FESTIVAL1_idx` (`FESTIVAL_IdFestivala` ASC) VISIBLE,
  INDEX `fk_ORGANIZATOR_has_FESTIVAL_ORGANIZATOR1_idx` (`ORGANIZATOR_IdOrganizatora` ASC) VISIBLE,
  CONSTRAINT `fk_ORGANIZATOR_has_FESTIVAL_FESTIVAL1`
    FOREIGN KEY (`FESTIVAL_IdFestivala`)
    REFERENCES `festivaldb`.`festival` (`IdFestivala`),
  CONSTRAINT `fk_ORGANIZATOR_has_FESTIVAL_ORGANIZATOR1`
    FOREIGN KEY (`ORGANIZATOR_IdOrganizatora`)
    REFERENCES `festivaldb`.`organizator` (`IdOrganizatora`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`sponzor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`sponzor` (
  `IdSponzora` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  `IdGrada` INT NULL DEFAULT NULL,
  `WebSite` VARCHAR(500) NULL DEFAULT NULL,
  PRIMARY KEY (`IdSponzora`),
  INDEX `IdGrada_idx` (`IdGrada` ASC) VISIBLE,
  CONSTRAINT `FK_Sponzor_IdGrada`
    FOREIGN KEY (`IdGrada`)
    REFERENCES `festivaldb`.`grad` (`IdGrada`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`sponzorski_ugovor`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`sponzorski_ugovor` (
  `IdSponzorskogUgovora` INT NOT NULL AUTO_INCREMENT,
  `Vrijednost` DECIMAL(6,0) NOT NULL,
  `DatumOd` DATE NULL DEFAULT NULL,
  `DatumDo` DATE NULL DEFAULT NULL,
  `IdSponzora` INT NULL DEFAULT NULL,
  PRIMARY KEY (`IdSponzorskogUgovora`),
  INDEX `IdSponzora_idx` (`IdSponzora` ASC) VISIBLE,
  CONSTRAINT `FK_SponzorskiUgovor_IdSponzora`
    FOREIGN KEY (`IdSponzora`)
    REFERENCES `festivaldb`.`sponzor` (`IdSponzora`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`ulaznica`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`ulaznica` (
  `IdUlaznice` INT NOT NULL AUTO_INCREMENT,
  `IdFestivala` INT NOT NULL,
  `IdVrsteUlaznice` INT NOT NULL,
  PRIMARY KEY (`IdUlaznice`))
ENGINE = InnoDB
AUTO_INCREMENT = 50
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`zaduzenje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`zaduzenje` (
  `IdZaduzenja` INT NOT NULL AUTO_INCREMENT,
  `Naziv` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IdZaduzenja`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`zaposleni`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`zaposleni` (
  `IdZaposlenog` INT NOT NULL,
  `Ime` VARCHAR(45) NOT NULL,
  `Prezime` VARCHAR(45) NOT NULL,
  PRIMARY KEY (`IdZaposlenog`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`zaposleni_festival`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`zaposleni_festival` (
  `ZAPOSLENI_IdZaposlenog` INT NOT NULL,
  `FESTIVAL_IdFestivala` INT NOT NULL,
  PRIMARY KEY (`ZAPOSLENI_IdZaposlenog`, `FESTIVAL_IdFestivala`),
  INDEX `fk_ZAPOSLENI_has_FESTIVAL_FESTIVAL1_idx` (`FESTIVAL_IdFestivala` ASC) VISIBLE,
  INDEX `fk_ZAPOSLENI_has_FESTIVAL_ZAPOSLENI1_idx` (`ZAPOSLENI_IdZaposlenog` ASC) VISIBLE,
  CONSTRAINT `fk_ZAPOSLENI_has_FESTIVAL_FESTIVAL1`
    FOREIGN KEY (`FESTIVAL_IdFestivala`)
    REFERENCES `festivaldb`.`festival` (`IdFestivala`),
  CONSTRAINT `fk_ZAPOSLENI_has_FESTIVAL_ZAPOSLENI1`
    FOREIGN KEY (`ZAPOSLENI_IdZaposlenog`)
    REFERENCES `festivaldb`.`zaposleni` (`IdZaposlenog`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


-- -----------------------------------------------------
-- Table `festivaldb`.`zaposleni_zaduzenje`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `festivaldb`.`zaposleni_zaduzenje` (
  `ZAPOSLENI_IdZaposlenog` INT NOT NULL,
  `ZADUZENJE_IdZaduzenja` INT NOT NULL,
  `IdZaposleniZaduzenje` INT NOT NULL AUTO_INCREMENT,
  `DatumPocetka` DATE NULL DEFAULT NULL,
  `DatumZavrsetka` DATE NULL DEFAULT NULL,
  PRIMARY KEY (`IdZaposleniZaduzenje`),
  INDEX `fk_ZAPOSLENI_has_ZADUZENJE_ZADUZENJE1_idx` (`ZADUZENJE_IdZaduzenja` ASC) VISIBLE,
  INDEX `fk_ZAPOSLENI_has_ZADUZENJE_ZAPOSLENI1_idx` (`ZAPOSLENI_IdZaposlenog` ASC) VISIBLE,
  CONSTRAINT `fk_ZAPOSLENI_has_ZADUZENJE_ZADUZENJE1`
    FOREIGN KEY (`ZADUZENJE_IdZaduzenja`)
    REFERENCES `festivaldb`.`zaduzenje` (`IdZaduzenja`),
  CONSTRAINT `fk_ZAPOSLENI_has_ZADUZENJE_ZAPOSLENI1`
    FOREIGN KEY (`ZAPOSLENI_IdZaposlenog`)
    REFERENCES `festivaldb`.`zaposleni` (`IdZaposlenog`))
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8mb3;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;
