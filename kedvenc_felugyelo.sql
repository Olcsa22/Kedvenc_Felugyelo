-- phpMyAdmin SQL Dump
-- version 5.1.3
-- https://www.phpmyadmin.net/
--
-- Gép: localhost
-- Létrehozás ideje: 2022. Máj 12. 16:57
-- Kiszolgáló verziója: 10.4.24-MariaDB
-- PHP verzió: 7.4.29

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Adatbázis: `kedvenc_felugyelo`
--
CREATE DATABASE IF NOT EXISTS `kedvenc_felugyelo` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci;
USE `kedvenc_felugyelo`;

DELIMITER $$
--
-- Eljárások
--
CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteCat` (IN `catIdIn` INT)   DELETE FROM cat
where cat.id = catIdIn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteDog` (IN `dogIdIn` INT)   DELETE FROM dog
where dog.id = dogIdIn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `deleteHamster` (IN `hamsterIdIn` INT)   DELETE FROM hamster
WHERE hamster.id = hamsterIdIn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `insertUserWithRole` (IN `idIn` INT, IN `roleIdIn` INT)   INSERT INTO user_roles VALUES (idIn, roleIdIn)$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateCatFeeding` (IN `catIdIn` INT, IN `lastFeedingIn` DATETIME)   UPDATE cat
set cat.lastFeeding = lastFeedingIn
WHERE cat.id = catIdIn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateCatVaccinated` (IN `catIdIn` INT, IN `lastVaccinatedIn` DATE)   UPDATE cat
SET cat.vaccinated = lastVaccinatedIn
WHERE cat.id = catIdIn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateDogFeeding` (IN `dogIdIn` INT, IN `lastFeedingIn` DATETIME)   update dog
set dog.lastFeeding = lastFeedingIn
where dog.id = dogIdIn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateDogVaccinated` (IN `dogIdIn` INT, IN `lastVaccinatedIn` DATE)   UPDATE dog
SET dog.vaccinated = lastVaccinatedIn
WHERE dog.id = dogIdIn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateDogWalking` (IN `dogIdIn` INT, IN `lastWalkingIn` DATE)   update dog 
set dog.lastWalking = lastWalkingIn
where dog.id = dogIdIn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateHamsterCleaning` (IN `hamsterIdIn` INT, IN `lastCleaningIn` DATE)   UPDATE hamster
SET hamster.lastCleaning = lastCleaningIn
WHERE hamster.id = hamsterIdIn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateHamsterFeeding` (IN `hamsterIdIn` INT, IN `lastFeedingIn` DATETIME)   UPDATE hamster
SET hamster.lastFeeding = lastFeedingIn
WHERE hamster.id = hamsterIdIn$$

CREATE DEFINER=`root`@`localhost` PROCEDURE `updateHamsterToothWearer` (IN `hamsterIdIn` INT, IN `toothwearerChanged` DATE)   UPDATE hamster
SET hamster.toothwearerChanged = toothwearerChanged
WHERE hamster.id = hamsterIdIn$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `cat`
--

CREATE TABLE `cat` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `lastFeeding` datetime NOT NULL,
  `indoor` tinyint(4) NOT NULL,
  `feedingInterval` int(11) NOT NULL,
  `ownerID` int(11) NOT NULL,
  `vaccinated` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `cat`
--

INSERT INTO `cat` (`id`, `name`, `lastFeeding`, `indoor`, `feedingInterval`, `ownerID`, `vaccinated`) VALUES
(1, 'Cirmi', '2022-05-12 18:47:00', 1, 4, 1, '2022-05-12');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `dog`
--

CREATE TABLE `dog` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `lastFeeding` datetime NOT NULL,
  `feedingInterval` int(11) NOT NULL,
  `ownerID` int(11) NOT NULL,
  `indoor` tinyint(4) NOT NULL,
  `vaccinated` date NOT NULL,
  `lastWalking` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `dog`
--

INSERT INTO `dog` (`id`, `name`, `lastFeeding`, `feedingInterval`, `ownerID`, `indoor`, `vaccinated`, `lastWalking`) VALUES
(1, 'Zsömi', '2022-05-12 18:47:00', 3, 1, 1, '2022-05-12', '2022-05-12');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `hamster`
--

CREATE TABLE `hamster` (
  `id` int(11) NOT NULL,
  `ownerID` int(11) NOT NULL,
  `lastFeeding` datetime NOT NULL,
  `feedingInterval` int(11) NOT NULL,
  `lastCleaning` date NOT NULL,
  `name` varchar(50) NOT NULL,
  `toothwearerChanged` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `hamster`
--

INSERT INTO `hamster` (`id`, `ownerID`, `lastFeeding`, `feedingInterval`, `lastCleaning`, `name`, `toothwearerChanged`) VALUES
(1, 1, '2022-05-12 18:48:00', 3, '2022-05-12', 'Guszti', '2022-05-12');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `roles`
--

CREATE TABLE `roles` (
  `id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `roles`
--

INSERT INTO `roles` (`id`, `name`) VALUES
(1, 'ROLE_ADMIN'),
(2, 'ROLE_USER');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user`
--

CREATE TABLE `user` (
  `id` int(11) NOT NULL,
  `username` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(300) NOT NULL,
  `register` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `user`
--

INSERT INTO `user` (`id`, `username`, `email`, `password`, `register`) VALUES
(1, 'Oliver', 'kukacka5501@gmail.com', '$2a$10$PgR5lrsVe7FD4a.og54H1.QkSQhzipPhG71VVjWmX9sMRozVj1WEu', '2022-05-12');

-- --------------------------------------------------------

--
-- Tábla szerkezet ehhez a táblához `user_roles`
--

CREATE TABLE `user_roles` (
  `user_id` int(11) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- A tábla adatainak kiíratása `user_roles`
--

INSERT INTO `user_roles` (`user_id`, `role_id`) VALUES
(1, 1);

--
-- Indexek a kiírt táblákhoz
--

--
-- A tábla indexei `cat`
--
ALTER TABLE `cat`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `dog`
--
ALTER TABLE `dog`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `hamster`
--
ALTER TABLE `hamster`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `roles`
--
ALTER TABLE `roles`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- A tábla indexei `user_roles`
--
ALTER TABLE `user_roles`
  ADD PRIMARY KEY (`user_id`);

--
-- A kiírt táblák AUTO_INCREMENT értéke
--

--
-- AUTO_INCREMENT a táblához `cat`
--
ALTER TABLE `cat`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `dog`
--
ALTER TABLE `dog`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `hamster`
--
ALTER TABLE `hamster`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT a táblához `roles`
--
ALTER TABLE `roles`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT a táblához `user`
--
ALTER TABLE `user`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
