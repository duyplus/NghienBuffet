USE master
GO
CREATE DATABASE Nghien_Buffet
GO
USE Nghien_Buffet
GO
CREATE TABLE NhanVien (
	MaNV varchar(10) NOT NULL,
	HoTen nvarchar(100) NOT NULL,
	NgaySinh date NOT NULL,
	GioiTinh bit NOT NULL,
	SDT varchar(10) NOT NULL,
	Email varchar(100) NOT NULL,
	DiaChi nvarchar(255) NOT NULL,
	Hinh varchar(50) NOT NULL
	CONSTRAINT PK_NhanVien PRIMARY KEY CLUSTERED (MaNV)
)
GO
CREATE TABLE KhachHang (
	STT int identity(1,1) NOT NULL,
	SDT varchar(10) NOT NULL,
	TenKH nvarchar(100) NOT NULL,
	Email varchar(100) NOT NULL,
	CONSTRAINT PK_KhachHang PRIMARY KEY CLUSTERED (SDT)
)
GO
CREATE TABLE Ban (
	MaBan varchar(2) NOT NULL,
	SDT varchar(10) NOT NULL,
	MaNV varchar(10) NOT NULL,
	SoBan varchar(2) NOT NULL,
	GioDat time NOT NULL,
	SoLuong int NOT NULL,
	NguoiLon int NOT NULL,
	TreEm int NOT NULL,
	CONSTRAINT PK_Ban PRIMARY KEY CLUSTERED (MaBan)
)
GO
CREATE TABLE MonThem (
	MaMT varchar(10) NOT NULL,
	TenMT nvarchar(100) NOT NULL,
	Gia int NOT NULL,
	CONSTRAINT PK_MonThem PRIMARY KEY CLUSTERED (MaMT)
)
GO
CREATE TABLE MonDaThem (
	MaMDT int identity(1,1) NOT NULL,
	MaBan varchar(2) NOT NULL,
	MaMT varchar(10) NOT NULL,
	SoLuong int NOT NULL,
	CONSTRAINT PK_MonDaThem PRIMARY KEY CLUSTERED (MaMDT)
)
GO
CREATE TABLE HoaDon (
	MaHD int IDENTITY(1,1) NOT NULL,
	MaNV varchar(10) NOT NULL,
	SoBan varchar(2) NOT NULL,
	GioVao time NOT NULL,
	NgayXuat date NOT NULL,
	NguoiLon int NOT NULL,
	TreEm int NOT NULL,
	TongVe int NOT NULL,
	TongTien float NOT NULL,
	SDT varchar(10) NOT NULL,
	CONSTRAINT PK_HonDon PRIMARY KEY CLUSTERED (MaHD)
)
GO
CREATE TABLE Ve (
	MaVe varchar(10) NOT NULL,
	LoaiVe nvarchar(30) NOT NULL,
	Gia int NOT NULL,
	CONSTRAINT PK_Ve PRIMARY KEY CLUSTERED (MaVe)
)
GO
CREATE TABLE Ca (
	MaCa int IDENTITY(1,1) NOT NULL,
	NgayLam date NOT NULL,
	CaLam nvarchar(20) NOT NULL,
	BatDau time NOT NULL,
	KetThuc time NOT NULL,
	MaNV varchar(10) NOT NULL,
	CONSTRAINT PK_Ca PRIMARY KEY CLUSTERED (MaCa)
)
GO
CREATE TABLE NguyenLieu (
	MaNL varchar(10) NOT NULL,
	TenNL nvarchar(100) NOT NULL,
	SoLuong int NOT NULL,
	DVT nvarchar(100) NOT NULL,
	HSD date NOT NULL,
	MaDT varchar(10) NOT NULL,
	CONSTRAINT PK_Kho PRIMARY KEY CLUSTERED (MaNL)
)
GO
CREATE TABLE DoiTac (
	MaDT varchar(10) NOT NULL,
	TenDT nvarchar(100) NOT NULL,
	SDT varchar(11) NOT NULL,
	Email varchar(100) NOT NULL,
	DiaChi nvarchar(255) NOT NULL,
	CONSTRAINT PK_DoiTac PRIMARY KEY CLUSTERED (MaDT)
)
GO
CREATE TABLE Kho (
	MaXN int IDENTITY(1,1) NOT NULL,
	NgayXN date NOT NULL,
	SoLuong int NOT NULL,
	TrangThai nvarchar(10) NOT NULL,
	TiGia int NOT NULL,
	TongGia int,
	MaNL varchar(10) NOT NULL,
	MaNV varchar(10) NOT NULL,
	CONSTRAINT PK_NhapKho PRIMARY KEY CLUSTERED (MaXN)
)
GO
CREATE TABLE NguoiDung (
	TaiKhoan varchar(10) NOT NULL,
	MatKhau varchar(50) NOT NULL,
	HoTen nvarchar(100) NOT NULL,
	VaiTro bit NOT NULL,
	CONSTRAINT PK_NguoiDung PRIMARY KEY CLUSTERED (TaiKhoan)
)
GO
-- Thêm Nhân viên
INSERT INTO NhanVien (MaNV,HoTen,NgaySinh,GioiTinh,SDT,Email,DiaChi,Hinh) VALUES ('NHDuy',N'Nguyễn Hoàng Duy','1999-08-22',1,'0919993715','duynh@gmail.com',N'Bình Thuận','NoImage.png')
INSERT INTO NhanVien (MaNV,HoTen,NgaySinh,GioiTinh,SDT,Email,DiaChi,Hinh) VALUES ('DHPhi',N'Dương Huy Phi','2003-01-01',1,'0868605927','phidh@gmail.com',N'Đồng Nai','NoImage.png')
INSERT INTO NhanVien (MaNV,HoTen,NgaySinh,GioiTinh,SDT,Email,DiaChi,Hinh) VALUES ('NDDuy',N'Nguyễn Đức Duy','2003-02-02',1,'0368398962','duynd@gmail.com',N'Tây Ninh','NoImage.png')
INSERT INTO NhanVien (MaNV,HoTen,NgaySinh,GioiTinh,SDT,Email,DiaChi,Hinh) VALUES ('NDDLoi',N'Nguyễn Đào Danh Lợi','2003-03-03',1,'0799868232','loindd@gmail.com',N'Tây Ninh','NoImage.png')
INSERT INTO NhanVien (MaNV,HoTen,NgaySinh,GioiTinh,SDT,Email,DiaChi,Hinh) VALUES ('NHLinh',N'Ngô Hồng Linh','2003-04-04',1,'0911640321','linhnh@gmail.com',N'Lâm Đồng','NoImage.png')
INSERT INTO NhanVien (MaNV,HoTen,NgaySinh,GioiTinh,SDT,Email,DiaChi,Hinh) VALUES ('LTNhi',N'Lê Thị Nhi','2002-05-05',0,'0361237894','nhilta@gmail.com',N'Hồ Chí Minh','NoImage.png')
INSERT INTO NhanVien (MaNV,HoTen,NgaySinh,GioiTinh,SDT,Email,DiaChi,Hinh) VALUES ('MTTran',N'Mai Tố Trân','2001-06-06',0,'0921472583','tranmt@gmail.com',N'Quảng Ngãi','NoImage.png')
INSERT INTO NhanVien (MaNV,HoTen,NgaySinh,GioiTinh,SDT,Email,DiaChi,Hinh) VALUES ('TBNgoc',N'Trần Bảo Ngọc','2004-07-07',0,'0772583697','ngoctb@gmail.com',N'Vũng Tàu','NoImage.png')
INSERT INTO NhanVien (MaNV,HoTen,NgaySinh,GioiTinh,SDT,Email,DiaChi,Hinh) VALUES ('NQTuan',N'Nguyễn Quốc Tuấn','2000-07-07',0,'0563571595','tuannq@gmail.com',N'Hồ Chí Minh','NoImage.png')
-- Thêm Khách hàng
INSERT INTO KhachHang(SDT,TenKH,Email) VALUES ('0451345488',N'Trần Tiến Đạt','dat@gmail.com')
INSERT INTO KhachHang(SDT,TenKH,Email) VALUES ('0924561347',N'Nguyễn Ngọc Bình','binh@gmail.com')
INSERT INTO KhachHang(SDT,TenKH,Email) VALUES ('0984564823',N'Đỗ Thị Lan Hương','huong@gmail.com')
INSERT INTO KhachHang(SDT,TenKH,Email) VALUES ('0341258564',N'Lê Thị Ái Chi','chi@gmail.com')
INSERT INTO KhachHang(SDT,TenKH,Email) VALUES ('0523456851',N'Thạch Nhật Tiến','tien@gmail.com')
INSERT INTO KhachHang(SDT,TenKH,Email) VALUES ('0615267854',N'Trần Ngọc Toàn','toan@gmail.com')
INSERT INTO KhachHang(SDT,TenKH,Email) VALUES ('0363456789',N'Lê Tấn Hoàng','hoang@gmail.com')
INSERT INTO KhachHang(SDT,TenKH,Email) VALUES ('0913456799',N'Đặng Lê Anh','anh@gmail.com')
INSERT INTO KhachHang(SDT,TenKH,Email) VALUES ('0333456779',N'Bùi Quốc Huy','huy@gmail.com')
INSERT INTO KhachHang(SDT,TenKH,Email) VALUES ('0923456778',N'Trần Thị Thu','thu@gmail.com')
-- Thêm Vé
INSERT INTO Ve (MaVe,LoaiVe,Gia) VALUES ('1',N'Người lớn',300000)
INSERT INTO Ve (MaVe,LoaiVe,Gia) VALUES ('2',N'Trẻ em',150000)
-- Thêm Bàn
INSERT INTO Ban(MaBan,SDT,SoBan,GioDat,SoLuong,NguoiLon,TreEm,MaNV) VALUES ('01','0363456789','01','12:30',8,5,3,'NHDuy')
INSERT INTO Ban(MaBan,SDT,SoBan,GioDat,SoLuong,NguoiLon,TreEm,MaNV) VALUES ('02','0913456799','02','18:30',4,3,1,'DHPhi')
INSERT INTO Ban(MaBan,SDT,SoBan,GioDat,SoLuong,NguoiLon,TreEm,MaNV) VALUES ('03','0333456779','03','10:30',2,2,0,'NDDLoi')
INSERT INTO Ban(MaBan,SDT,SoBan,GioDat,SoLuong,NguoiLon,TreEm,MaNV) VALUES ('04','0923456778','04','17:30',5,2,3,'NHLinh')
-- Thêm Món thêm
INSERT INTO MonThem(MaMT,TenMT,Gia) VALUES ('M01',N'Bún',5000)
INSERT INTO MonThem(MaMT,TenMT,Gia) VALUES ('M02',N'Nước ngọt',15000)
INSERT INTO MonThem(MaMT,TenMT,Gia) VALUES ('M03',N'Bia',15000)
INSERT INTO MonThem(MaMT,TenMT,Gia) VALUES ('M04',N'Khăn ướt',3000)
INSERT INTO MonThem(MaMT,TenMT,Gia) VALUES ('M05',N'Thịt',30000)
INSERT INTO MonThem(MaMT,TenMT,Gia) VALUES ('M06',N'Tôm',40000)
INSERT INTO MonThem(MaMT,TenMT,Gia) VALUES ('M07',N'Cá',30000)
INSERT INTO MonThem(MaMT,TenMT,Gia) VALUES ('M08',N'Nước lẫu',10000)
-- Thêm Món đã thêm
SET IDENTITY_INSERT MonDaThem ON
INSERT INTO MonDaThem(MaMDT,MaBan,MaMT,SoLuong) VALUES (1,'01','M01',2)
INSERT INTO MonDaThem(MaMDT,MaBan,MaMT,SoLuong) VALUES (2,'01','M02',5)
INSERT INTO MonDaThem(MaMDT,MaBan,MaMT,SoLuong) VALUES (3,'02','M02',3)
INSERT INTO MonDaThem(MaMDT,MaBan,MaMT,SoLuong) VALUES (4,'02','M03',4)
INSERT INTO MonDaThem(MaMDT,MaBan,MaMT,SoLuong) VALUES (5,'02','M04',2)
SET IDENTITY_INSERT MonDaThem OFF
-- Thêm Hoá đơn
SET IDENTITY_INSERT HoaDon ON
INSERT INTO HoaDon (MaHD,MaNV,SoBan,GioVao,NgayXuat,NguoiLon,TreEm,TongVe,TongTien,SDT) VALUES (1,'NHLinh','01','12:30','2019-08-24',5,3,8,1605000,N'0451345488')
INSERT INTO HoaDon (MaHD,MaNV,SoBan,GioVao,NgayXuat,NguoiLon,TreEm,TongVe,TongTien,SDT) VALUES (2,'NHDuy','04','12:30','2019-10-22',5,3,8,1605000,N'0363456789')
INSERT INTO HoaDon (MaHD,MaNV,SoBan,GioVao,NgayXuat,NguoiLon,TreEm,TongVe,TongTien,SDT) VALUES (3,'NDDLoi','03','18:30','2019-12-27',3,1,4,750000,N'0913456799')
INSERT INTO HoaDon (MaHD,MaNV,SoBan,GioVao,NgayXuat,NguoiLon,TreEm,TongVe,TongTien,SDT) VALUES (4,'NHLinh','05','10:30','2020-02-25',2,0,2,400000,N'0333456779')
INSERT INTO HoaDon (MaHD,MaNV,SoBan,GioVao,NgayXuat,NguoiLon,TreEm,TongVe,TongTien,SDT) VALUES (5,'NDDuy','05','10:30','2020-03-02',2,0,2,400000,N'0923456778')
INSERT INTO HoaDon (MaHD,MaNV,SoBan,GioVao,NgayXuat,NguoiLon,TreEm,TongVe,TongTien,SDT) VALUES (6,'DHPhi','02','18:30','2020-05-22',3,1,4,750000,N'0924561347')
INSERT INTO HoaDon (MaHD,MaNV,SoBan,GioVao,NgayXuat,NguoiLon,TreEm,TongVe,TongTien,SDT) VALUES (7,'NDDLoi','03','10:30','2020-10-28',2,0,2,400000,N'0984564823')
INSERT INTO HoaDon (MaHD,MaNV,SoBan,GioVao,NgayXuat,NguoiLon,TreEm,TongVe,TongTien,SDT) VALUES (8,'DHPhi','01','19:10','2021-11-20',4,2,6,1605000,N'0341258564')
INSERT INTO HoaDon (MaHD,MaNV,SoBan,GioVao,NgayXuat,NguoiLon,TreEm,TongVe,TongTien,SDT) VALUES (9,'NDDuy','02','20:05','2021-11-22',7,2,9,2050000,N'0523456851')
INSERT INTO HoaDon (MaHD,MaNV,SoBan,GioVao,NgayXuat,NguoiLon,TreEm,TongVe,TongTien,SDT) VALUES (10,'NDDuy','03','09:30','2021-11-23',2,3,5,1150000,N'0615267854')
SET IDENTITY_INSERT HoaDon OFF
-- Thêm Ca
SET IDENTITY_INSERT Ca ON
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (1,'2021-11-14',N'Sáng','08:00','15:00','NHDuy')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (2,'2021-11-14',N'Sáng','08:00','15:00','NHLinh')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (3,'2021-11-14',N'Sáng','08:00','15:00','NDDLoi')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (4,'2021-11-14',N'Sáng','08:00','15:00','LTNhi')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (5,'2021-11-15',N'Chiều','15:00','22:00','NDDLoi')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (6,'2021-11-15',N'Chiều','15:00','22:00','NHDuy')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (7,'2021-11-15',N'Chiều','15:00','22:00','MTTran')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (8,'2021-11-15',N'Chiều','15:00','22:00','TBNgoc')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (9,'2021-11-16',N'Sáng','08:00','15:00','MTTran')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (10,'2021-11-16',N'Sáng','08:00','22:00','NDDuy')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (11,'2021-11-16',N'Sáng','08:00','22:00','NHLinh')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (12,'2021-11-16',N'Sáng','08:00','15:00','TBNgoc')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (13,'2021-11-17',N'Chiều','15:00','22:00','DHPhi')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (14,'2021-11-17',N'Chiều','15:00','22:00','NDDuy')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (15,'2021-11-17',N'Chiều','15:00','22:00','LTNhi')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (16,'2021-11-17',N'Chiều','15:00','22:00','NDDLoi')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (17,'2021-11-18',N'Sáng','08:00','15:00','LTNhi')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (18,'2021-11-18',N'Sáng','08:00','15:00','MTTran')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (19,'2021-11-18',N'Sáng','08:00','15:00','NHDuy')
INSERT INTO Ca (MaCa,NgayLam,CaLam,BatDau,KetThuc,MaNV) VALUES (20,'2021-11-18',N'Sáng','08:00','15:00','NHLinh')
SET IDENTITY_INSERT Ca OFF
-- Thêm Đối tác
INSERT INTO DoiTac (MaDT,TenDT,SDT,Email,DiaChi) VALUES ('DT01',N'Cty VISSAN','0123456789','vissanco@vissan.com.vn',N'Hồ Chí Minh')
INSERT INTO DoiTac (MaDT,TenDT,SDT,Email,DiaChi) VALUES ('DT02',N'Cty Tân Hiệp Phát','0234567891','info@thp.com.vn',N'Bình Dương')
INSERT INTO DoiTac (MaDT,TenDT,SDT,Email,DiaChi) VALUES ('DT03',N'Cty Đảo Hải Sản','0987654321','contact@daohaisan.vn',N'Tân Bình,HCM')
INSERT INTO DoiTac (MaDT,TenDT,SDT,Email,DiaChi) VALUES ('DT04',N'Cty Đà Lạt GAP','0456789123','cs@dalatgapstore.com',N'Đà Lạt')
INSERT INTO DoiTac (MaDT,TenDT,SDT,Email,DiaChi) VALUES ('DT05',N'Cty ANAN FOOD','0147258369','kinhbactofu@gmail.com',N'Thủ Đức,HCM')
INSERT INTO DoiTac (MaDT,TenDT,SDT,Email,DiaChi) VALUES ('DT06',N'Cty Ong Mật','02623817990','info@dakhoney.com',N'Daklak')
INSERT INTO DoiTac (MaDT,TenDT,SDT,Email,DiaChi) VALUES ('DT07',N'Cty PTFISACO','02523812807','concavang.ptfisaco@gmail.com',N'Phan Thiết')
INSERT INTO DoiTac (MaDT,TenDT,SDT,Email,DiaChi) VALUES ('DT08',N'Cty An Hưng Phước','02963935367','ahpfishoil@gmail.com',N'Đồng Tháp')
-- Thêm Nguyên liệu
INSERT INTO NguyenLieu (MaNL,TenNL,HSD,SoLuong,DVT,MaDT) VALUES ('NL1',N'Rau tươi','2022-10-18',150,N'Kg','DT04')
INSERT INTO NguyenLieu (MaNL,TenNL,HSD,SoLuong,DVT,MaDT) VALUES ('NL2',N'Thịt bò','2022-05-20',200,N'Kg','DT01')
INSERT INTO NguyenLieu (MaNL,TenNL,HSD,SoLuong,DVT,MaDT) VALUES ('NL3',N'Nước ngọt','2024-04-10',100,N'Chai','DT02')
INSERT INTO NguyenLieu (MaNL,TenNL,HSD,SoLuong,DVT,MaDT) VALUES ('NL4',N'Bún tươi','2022-07-25',100,N'Kg','DT05')
INSERT INTO NguyenLieu (MaNL,TenNL,HSD,SoLuong,DVT,MaDT) VALUES ('NL5',N'Tôm sú','2022-08-02',100,N'Kg','DT03')
INSERT INTO NguyenLieu (MaNL,TenNL,HSD,SoLuong,DVT,MaDT) VALUES ('NL6',N'Hành tây','2022-12-22',100,N'Kg','DT04')
INSERT INTO NguyenLieu (MaNL,TenNL,HSD,SoLuong,DVT,MaDT) VALUES ('NL7',N'Muối','2023-07-14',100,N'Kg','DT05')
INSERT INTO NguyenLieu (MaNL,TenNL,HSD,SoLuong,DVT,MaDT) VALUES ('NL8',N'Tỏi','2022-05-17',10,N'Kg','DT05')
INSERT INTO NguyenLieu (MaNL,TenNL,HSD,SoLuong,DVT,MaDT) VALUES ('NL9',N'Mật ong','2024-02-05',0,N'Lít','DT06')
INSERT INTO NguyenLieu (MaNL,TenNL,HSD,SoLuong,DVT,MaDT) VALUES ('NL10',N'Sốt BBQ','2022-10-13',100,N'Gói','DT05')
-- Thêm Kho
SET IDENTITY_INSERT Kho ON
INSERT INTO Kho (MaXN,NgayXN,SoLuong,TrangThai,TiGia,TongGia,MaNL,MaNV) VALUES (1,'2021-10-05',40,N'Nhập',18000,360000,'NL3','NQTuan')
INSERT INTO Kho (MaXN,NgayXN,SoLuong,TrangThai,TiGia,TongGia,MaNL,MaNV) VALUES (2,'2021-10-12',50,N'Nhập',70000,925000,'NL4','NQTuan')
INSERT INTO Kho (MaXN,NgayXN,SoLuong,TrangThai,TiGia,TongGia,MaNL,MaNV) VALUES (3,'2021-11-15',50,N'Xuất',70000,900000,'NL1','NQTuan')
INSERT INTO Kho (MaXN,NgayXN,SoLuong,TrangThai,TiGia,TongGia,MaNL,MaNV) VALUES (4,'2021-12-03',25,N'Xuất',70000,2250000,'NL2','NQTuan')
INSERT INTO Kho (MaXN,NgayXN,SoLuong,TrangThai,TiGia,TongGia,MaNL,MaNV) VALUES (5,'2021-12-03',15,N'Xuất',70000,330000,'NL5','NQTuan')
INSERT INTO Kho (MaXN,NgayXN,SoLuong,TrangThai,TiGia,TongGia,MaNL,MaNV) VALUES (6,'2021-12-03',20,N'Xuất',70000,1400000,'NL10','NQTuan')
INSERT INTO Kho (MaXN,NgayXN,SoLuong,TrangThai,TiGia,TongGia,MaNL,MaNV) VALUES (7,'2021-12-03',7,N'Xuất',70000,175000,'NL8','NQTuan')
INSERT INTO Kho (MaXN,NgayXN,SoLuong,TrangThai,TiGia,TongGia,MaNL,MaNV) VALUES (8,'2021-12-03',10,N'Xuất',70000,580000,'NL7','NQTuan')
SET IDENTITY_INSERT Kho OFF
-- Thêm Người dùng
INSERT INTO NguoiDung (TaiKhoan,MatKhau,HoTen,VaiTro) VALUES ('admin','123456',N'Nguyễn Văn Tèo',1)
INSERT INTO NguoiDung (TaiKhoan,MatKhau,HoTen,VaiTro) VALUES ('staff','123456',N'Bành Thị Nở',0)
GO

-- Khoá phụ bảng Hoá đơn
ALTER TABLE HoaDon WITH CHECK ADD CONSTRAINT FK_HoaDon_KhachHang FOREIGN KEY(SDT)
REFERENCES KhachHang(SDT) ON DELETE CASCADE
GO
ALTER TABLE HoaDon WITH CHECK ADD CONSTRAINT FK_HoaDon_NhanVien FOREIGN KEY(MaNV)
REFERENCES NhanVien(MaNV) ON DELETE CASCADE
GO
-- Khóa phụ bảng bàn
ALTER TABLE Ban WITH CHECK ADD CONSTRAINT FK_Ban_KhachHang FOREIGN KEY(SDT)
REFERENCES KhachHang(SDT) ON DELETE CASCADE
GO
--Khoá phụ bảng món đã thêm
ALTER TABLE MonDaThem WITH CHECK ADD CONSTRAINT FK_MonDaThem_Ban FOREIGN KEY(MaBan)
REFERENCES Ban(MaBan) ON DELETE CASCADE
GO
ALTER TABLE MonDaThem WITH CHECK ADD CONSTRAINT FK_MonDaThem_MonThem FOREIGN KEY(MaMT)
REFERENCES MonThem(MaMT) ON DELETE CASCADE
GO
-- Khoá phụ bảng Ca
ALTER TABLE Ca WITH CHECK ADD CONSTRAINT FK_Ca_NhanVien FOREIGN KEY(MaNV)
REFERENCES NhanVien(MaNV) ON UPDATE CASCADE ON DELETE CASCADE
GO
-- Khoá phụ bảng Nguyên liệu
ALTER TABLE NguyenLieu WITH CHECK ADD CONSTRAINT FK_NguyenLieu_DoiTac FOREIGN KEY(MaDT)
REFERENCES DoiTac(MaDT) ON UPDATE CASCADE ON DELETE CASCADE
GO
-- Khoá phụ bảng Kho
ALTER TABLE Kho WITH CHECK ADD CONSTRAINT FK_Kho_NguyenLieu FOREIGN KEY(MaNL)
REFERENCES NguyenLieu(MaNL) ON UPDATE CASCADE ON DELETE CASCADE
GO
ALTER TABLE Kho WITH CHECK ADD CONSTRAINT FK_Kho_NhanVien FOREIGN KEY(MaNV)
REFERENCES NhanVien(MaNV) ON UPDATE CASCADE
GO

-- DROP PROC sp_DoanhThuNgay
-- DROP PROC sp_DoanhThuThang
-- DROP PROC sp_DoanhThuNam

-- Thủ tục Doanh thu theo ngày
CREATE PROC sp_DoanhThuNgay
AS BEGIN
	SELECT
		NgayXuat NgayBan,
		COUNT(SDT) TongBan,
		MAX(TongVe) TongVe,
		MAX(TongTien) TongTien
	FROM HoaDon
		GROUP BY NgayXuat
		ORDER BY NgayXuat ASC
END
GO
-- Thủ tục Doanh thu theo tháng
CREATE PROC sp_DoanhThuThang
AS BEGIN
	SELECT
		CONCAT(FORMAT(NgayXuat,'MM'),'/',FORMAT(NgayXuat,'yyyy')) ThangBan,
		COUNT(SDT) TongBan,
		MAX(TongVe) TongVe,
		MAX(TongTien) TongTien
	FROM HoaDon
		GROUP BY CONCAT(FORMAT(NgayXuat,'MM'),'/',FORMAT(NgayXuat,'yyyy'))
		ORDER BY CONCAT(FORMAT(NgayXuat,'MM'),'/',FORMAT(NgayXuat,'yyyy')) ASC
END
GO
-- Thủ tục Doanh thu theo năm
CREATE PROC sp_DoanhThuNam
AS BEGIN
	SELECT
		YEAR(NgayXuat) NamBan,
		COUNT(SDT) TongBan,
		MAX(TongVe) TongVe,
		MAX(TongTien) TongTien
	FROM HoaDon
		GROUP BY YEAR(NgayXuat)
		ORDER BY YEAR(NgayXuat) ASC
END
GO
USE master
GO