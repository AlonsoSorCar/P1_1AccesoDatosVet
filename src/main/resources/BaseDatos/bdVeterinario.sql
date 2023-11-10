drop database veterinario;
create database veterinario;
use veterinario;

create table mascotas(
                         id int unsigned auto_increment primary key,
                         nombre varchar(45),
                         raza varchar(45),
                         peso decimal(4,2),
                         fechaN date,
                         causaConsulta varchar(45),
                         otros text
);


CREATE TABLE usuarios (
                          id int auto_increment primary key,
                          nombre varchar(255),
                          salt varchar(64), -- Campo para almacenar la sal
                          contrasena_hash varchar(64) -- el hashusuarios
);


INSERT INTO `veterinario`.`mascotas` (`nombre`, `raza`, `peso`, `fechaN`, `causaConsulta`, `otros`) VALUES ('zarpas', 'gato', '4.3', '2017-02-14', 'pata rota', 'agresivo');
INSERT INTO `veterinario`.`mascotas` (`nombre`, `raza`, `peso`, `fechaN`, `causaConsulta`, `otros`) VALUES ('bigotes', 'gato', '5.2', '2021-07-24', 'Neumonia', '');
INSERT INTO `veterinario`.`mascotas` (`nombre`, `raza`, `peso`, `fechaN`, `causaConsulta`, `otros`) VALUES ('max', 'perro', '12.9', '2022-07-24', 'corte pelo', 'usa bozal');
INSERT INTO `veterinario`.`mascotas` (`nombre`, `raza`, `peso`, `fechaN`, `causaConsulta`, `otros`) VALUES ('pepe', 'canario', '0.19', '2022-09-04', 'desconocido', 'Jaula azul');
