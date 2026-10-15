/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/SQLTemplate.sql to edit this template
 */
/**
 * Author:  HP VICTUS
CREATE DATABASE IF NOT EXISTS db_salon;
USE db_salon;

DROP TABLE IF EXISTS reservasi;
DROP TABLE IF EXISTS layanan;
DROP TABLE IF EXISTS pelanggan;

CREATE TABLE pelanggan (
    id_pelanggan INT AUTO_INCREMENT PRIMARY KEY,
    nama VARCHAR(50) NOT NULL,
    no_telepon VARCHAR(15) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE layanan (
    id_layanan INT AUTO_INCREMENT PRIMARY KEY,
    nama_layanan VARCHAR(50) NOT NULL,
    harga INT NOT NULL,
    kategori VARCHAR(20) NOT NULL,
    panjang_rambut VARCHAR(20),
    durasi_menit INT
) ENGINE=InnoDB;

CREATE TABLE reservasi (
    id_reservasi INT AUTO_INCREMENT PRIMARY KEY,
    id_pelanggan INT NOT NULL,
    id_layanan INT NOT NULL,
    tanggal VARCHAR(10) NOT NULL,
    FOREIGN KEY (id_pelanggan) REFERENCES pelanggan(id_pelanggan) ON DELETE CASCADE,
    FOREIGN KEY (id_layanan) REFERENCES layanan(id_layanan) ON DELETE CASCADE
) ENGINE=InnoDB;

INSERT INTO pelanggan (nama, no_telepon) VALUES
('Ciaaw', '081234567890'),
('Mrow', '081298765432'),
('Chantip', '081345678901'),
('Casey', '081387654321'),
('Keity', '081456789013');

INSERT INTO layanan (nama_layanan, harga, kategori, panjang_rambut, durasi_menit) VALUES
('Potong Rambut', 50000, 'Rambut', 'Pendek', NULL),
('Creambath', 100000, 'Rambut', 'Sedang', NULL),
('Smoothing Rambut', 350000, 'Rambut', 'Panjang', NULL),
('Facial', 150000, 'Kecantikan', NULL, 60),
('Manicure Pedicure', 120000, 'Kecantikan', NULL, 90);

INSERT INTO reservasi (id_pelanggan, id_layanan, tanggal) VALUES
(1, 1, '01-10-2026'),
(2, 2, '02-10-2026'),
(3, 3, '03-10-2026'),
(4, 4, '04-10-2026'),
(5, 5, '05-10-2026');
