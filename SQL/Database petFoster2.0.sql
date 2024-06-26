USE [master]
GO
/****** Object:  Database [petfoster]    Script Date: 09/11/2023 1:02:12 AM ******/
CREATE DATABASE [petfoster_2.0]
 CONTAINMENT = NONE
 ON  PRIMARY 
( NAME = N'petfoster', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\petfoster.mdf' , SIZE = 8192KB , MAXSIZE = UNLIMITED, FILEGROWTH = 65536KB )
 LOG ON 
( NAME = N'petfoster_log', FILENAME = N'C:\Program Files\Microsoft SQL Server\MSSQL15.MSSQLSERVER\MSSQL\DATA\petfoster_log.ldf' , SIZE = 8192KB , MAXSIZE = 2048GB , FILEGROWTH = 65536KB )
 WITH CATALOG_COLLATION = DATABASE_DEFAULT
GO
ALTER DATABASE [petfoster] SET COMPATIBILITY_LEVEL = 150
GO
IF (1 = FULLTEXTSERVICEPROPERTY('IsFullTextInstalled'))
begin
EXEC [petfoster].[dbo].[sp_fulltext_database] @action = 'enable'
end
GO
ALTER DATABASE [petfoster] SET ANSI_NULL_DEFAULT OFF 
GO
ALTER DATABASE [petfoster] SET ANSI_NULLS OFF 
GO
ALTER DATABASE [petfoster] SET ANSI_PADDING OFF 
GO
ALTER DATABASE [petfoster] SET ANSI_WARNINGS OFF 
GO
ALTER DATABASE [petfoster] SET ARITHABORT OFF 
GO
ALTER DATABASE [petfoster] SET AUTO_CLOSE OFF 
GO
ALTER DATABASE [petfoster] SET AUTO_SHRINK OFF 
GO
ALTER DATABASE [petfoster] SET AUTO_UPDATE_STATISTICS ON 
GO
ALTER DATABASE [petfoster] SET CURSOR_CLOSE_ON_COMMIT OFF 
GO
ALTER DATABASE [petfoster] SET CURSOR_DEFAULT  GLOBAL 
GO
ALTER DATABASE [petfoster] SET CONCAT_NULL_YIELDS_NULL OFF 
GO
ALTER DATABASE [petfoster] SET NUMERIC_ROUNDABORT OFF 
GO
ALTER DATABASE [petfoster] SET QUOTED_IDENTIFIER OFF 
GO
ALTER DATABASE [petfoster] SET RECURSIVE_TRIGGERS OFF 
GO
ALTER DATABASE [petfoster] SET  ENABLE_BROKER 
GO
ALTER DATABASE [petfoster] SET AUTO_UPDATE_STATISTICS_ASYNC OFF 
GO
ALTER DATABASE [petfoster] SET DATE_CORRELATION_OPTIMIZATION OFF 
GO
ALTER DATABASE [petfoster] SET TRUSTWORTHY OFF 
GO
ALTER DATABASE [petfoster] SET ALLOW_SNAPSHOT_ISOLATION OFF 
GO
ALTER DATABASE [petfoster] SET PARAMETERIZATION SIMPLE 
GO
ALTER DATABASE [petfoster] SET READ_COMMITTED_SNAPSHOT OFF 
GO
ALTER DATABASE [petfoster] SET HONOR_BROKER_PRIORITY OFF 
GO
ALTER DATABASE [petfoster] SET RECOVERY FULL 
GO
ALTER DATABASE [petfoster] SET  MULTI_USER 
GO
ALTER DATABASE [petfoster] SET PAGE_VERIFY CHECKSUM  
GO
ALTER DATABASE [petfoster] SET DB_CHAINING OFF 
GO
ALTER DATABASE [petfoster] SET FILESTREAM( NON_TRANSACTED_ACCESS = OFF ) 
GO
ALTER DATABASE [petfoster] SET TARGET_RECOVERY_TIME = 60 SECONDS 
GO
ALTER DATABASE [petfoster] SET DELAYED_DURABILITY = DISABLED 
GO
ALTER DATABASE [petfoster] SET ACCELERATED_DATABASE_RECOVERY = OFF  
GO
EXEC sys.sp_db_vardecimal_storage_format N'petfoster', N'ON'
GO
ALTER DATABASE [petfoster] SET QUERY_STORE = OFF
GO
USE [petfoster]
GO
/****** Object:  Table [dbo].[addresses]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[addresses](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[is_default] [bit] NULL,
	[address] [nvarchar](255) NULL,
	[create_at] [datetime2](7) NULL,
	[district] [nvarchar](255) NULL,
	[phone] [varchar](255) NULL,
	[province] [nvarchar](255) NULL,
	[recipient] [varchar](255) NULL,
	[ward] [nvarchar](255) NULL,
	[user_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[adopt]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[adopt](
	[adopt_id] [int] IDENTITY(1,1) NOT NULL,
	[adopt_at] [datetime2](7) NULL,
	[register_at] [datetime2](7) NULL,
	[status] [nvarchar](255) NULL,
	[pet_id] [varchar](255) NULL,
	[user_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[adopt_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[authorities]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[authorities](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role_id] [int] NULL,
	[user_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[brand]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[brand](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[brand] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[cart_item]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[cart_item](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[quantity] [int] NULL,
	[cart_id] [int] NULL,
	[product_repo_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[carts]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[carts](
	[card_id] [int] IDENTITY(1,1) NOT NULL,
	[user_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[card_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[daily_report]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[daily_report](
	[orderstotal] [int] NOT NULL,
	[revenuetotal] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[orderstotal] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[delivery_company]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[delivery_company](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[company] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[donate]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[donate](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[descriptions] [nvarchar](255) NULL,
	[donate_amount] [float] NULL,
	[donate_at] [datetime2](7) NULL,
	[donater] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[favorite]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[favorite](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[pet_id] [varchar](255) NULL,
	[user_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[imgs]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[imgs](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[name_img] [varchar](255) NULL,
	[pet_id] [varchar](255) NULL,
	[product_id] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[order_detail]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[order_detail](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[price] [float] NULL,
	[quantity] [int] NULL,
	[total] [float] NULL,
	[order_id] [int] NULL,
	[product_repo_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[orders]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[orders](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[create_at] [datetime2](7) NULL,
	[descriptions] [nvarchar](255) NULL,
	[status] [nvarchar](255) NULL,
	[total] [float] NULL,
	[payment_id] [int] NULL,
	[shipping_info_id] [int] NULL,
	[user_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[payment]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[payment](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[amount] [float] NULL,
	[is_paid] [bit] NULL,
	[pay_at] [datetime2](7) NULL,
	[transaction_number] [nvarchar](255) NULL,
	[payment_method_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[payment_method]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[payment_method](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[method] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pet]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pet](
	[pet_id] [varchar](255) NOT NULL,
	[adopt_status] [nvarchar](255) NULL,
	[age] [nvarchar](255) NULL,
	[create_at] [datetime2](7) NULL,
	[descriptions] [nvarchar](255) NULL,
	[foster_at] [datetime2](7) NULL,
	[is_spay] [bit] NULL,
	[pet_color] [nvarchar](255) NULL,
	[pet_name] [varchar](255) NULL,
	[sex] [bit] NULL,
	[breed_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[pet_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pet_breed]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pet_breed](
	[breed_id] [varchar](255) NOT NULL,
	[breed_name] [nvarchar](255) NULL,
	[type_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[breed_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[pet_type]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[pet_type](
	[type_id] [varchar](255) NOT NULL,
	[type_name] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[price_change]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[price_change](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[new_in_price] [float] NULL,
	[new_out_price] [float] NULL,
	[old_in_price] [float] NULL,
	[old_out_price] [float] NULL,
	[update_at] [datetime2](7) NULL,
	[product_repo_id] [int] NULL,
	[user_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product](
	[product_id] [nvarchar](255) NOT NULL,
	[create_at] [datetime2](7) NULL,
	[product_desc] [nvarchar](255) NULL,
	[is_active] [bit] NULL,
	[product_name] [nvarchar](255) NULL,
	[brand_id] [int] NULL,
	[type_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[product_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_repo]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_repo](
	[product_repo_id] [int] IDENTITY(1,1) NOT NULL,
	[in_price] [float] NULL,
	[in_stock] [bit] NULL,
	[is_active] [bit] NULL,
	[out_price] [float] NULL,
	[quantity] [int] NULL,
	[size] [int] NULL,
	[product_id] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[product_repo_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[product_type]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[product_type](
	[product_type_id] [varchar](255) NOT NULL,
	[product_type_name] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[product_type_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[recent_view]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[recent_view](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[view_at] [datetime2](7) NULL,
	[product_id] [nvarchar](255) NULL,
	[user_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[revenue]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[revenue](
	[product_type_name] [varchar](255) NOT NULL,
	[total] [float] NULL,
PRIMARY KEY CLUSTERED 
(
	[product_type_name] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[review]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[review](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[comment] [nvarchar](255) NULL,
	[create_at] [datetime2](7) NULL,
	[rate] [int] NULL,
	[order_id] [int] NULL,
	[product_id] [nvarchar](255) NULL,
	[user_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[role]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[role](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[role] [nvarchar](255) NULL,
	[role_desc] [nvarchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[search_history]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[search_history](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[keyword] [nvarchar](255) NULL,
	[search_at] [datetime2](7) NULL,
	[user_id] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[shipping_info]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[shipping_info](
	[id] [int] IDENTITY(1,1) NOT NULL,
	[address] [nvarchar](255) NULL,
	[district] [nvarchar](255) NULL,
	[full_name] [nvarchar](255) NULL,
	[phone] [varchar](255) NULL,
	[province] [nvarchar](255) NULL,
	[ship_fee] [bigint] NULL,
	[ward] [nvarchar](255) NULL,
	[delivery_company_id] [int] NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[user_profile]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[user_profile](
	[id] [varchar](255) NOT NULL,
	[birthday] [datetime2](7) NULL,
	[email] [varchar](255) NULL,
	[fullname] [varchar](255) NULL,
	[gender] [bit] NOT NULL,
	[phone] [varchar](255) NULL,
	[username] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
/****** Object:  Table [dbo].[users]    Script Date: 09/11/2023 1:02:12 AM ******/
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
CREATE TABLE [dbo].[users](
	[user_id] [varchar](255) NOT NULL,
	[avatar] [varchar](255) NULL,
	[birthday] [datetime2](7) NULL,
	[create_at] [datetime2](7) NULL,
	[email] [varchar](255) NULL,
	[fullname] [nvarchar](255) NULL,
	[gender] [bit] NULL,
	[is_active] [bit] NULL,
	[is_email_verified] [bit] NULL,
	[password] [varchar](255) NULL,
	[phone] [varchar](255) NULL,
	[token] [varchar](255) NULL,
	[token_create_at] [datetime2](7) NULL,
	[username] [varchar](255) NULL,
PRIMARY KEY CLUSTERED 
(
	[user_id] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON, OPTIMIZE_FOR_SEQUENTIAL_KEY = OFF) ON [PRIMARY]
) ON [PRIMARY]
GO
SET IDENTITY_INSERT [dbo].[addresses] ON 

INSERT [dbo].[addresses] ([id], [is_default], [address], [create_at], [district], [phone], [province], [recipient], [ward], [user_id]) VALUES (1, 1, N'Số 85-87 Trần Hưng Đạo', CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2), N'Hoàn Kiếm', N'0911771155', N'TP. Hà Nội', N'Hu?nh Van Tuân', N'Chương Dương', N'userid00001')
INSERT [dbo].[addresses] ([id], [is_default], [address], [create_at], [district], [phone], [province], [recipient], [ward], [user_id]) VALUES (2, 0, N'Số 17 Hồ Thị Kỷ', CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2), N'Hoàn Kiếm', N'0911771155', N'TP. Hà Nội', N'Hu?nh Van Tuân', N'Chương Dương', N'userid00001')
INSERT [dbo].[addresses] ([id], [is_default], [address], [create_at], [district], [phone], [province], [recipient], [ward], [user_id]) VALUES (3, 1, N'Số 268 Trần Hưng Đạo', CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2), N'Q.1', N'0907193185', N'TP. HCM', N'Tr?n Van Vi?t', N'P. Nguyễn Cư Trinh', N'userid00002')
INSERT [dbo].[addresses] ([id], [is_default], [address], [create_at], [district], [phone], [province], [recipient], [ward], [user_id]) VALUES (4, 0, N'Số 02 Lê Đại Hành', CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2), N'Q.1', N'0907193185', N'TP. HCM', N'Tr?n Van Vi?t', N'P. Nguyễn Cư Trinh', N'userid00002')
INSERT [dbo].[addresses] ([id], [is_default], [address], [create_at], [district], [phone], [province], [recipient], [ward], [user_id]) VALUES (5, 1, N'Số 18 Lê Hồng Phong', CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2), N'Ninh Kiều', N'0939702086', N'TP. Hà Nội', N'H? Minh Tâm', N'P. Cái Khế,', N'userid00003')
INSERT [dbo].[addresses] ([id], [is_default], [address], [create_at], [district], [phone], [province], [recipient], [ward], [user_id]) VALUES (6, 0, N'Số 9A Trần Phú', CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2), N'Ninh Kiều', N'0939702086', N'TP. Hà Nội', N'H? Minh Tâm', N'P. Cái Khế,', N'userid00003')
SET IDENTITY_INSERT [dbo].[addresses] OFF
GO
SET IDENTITY_INSERT [dbo].[authorities] ON 

INSERT [dbo].[authorities] ([id], [role_id], [user_id]) VALUES (1, 1, N'superadmin')
INSERT [dbo].[authorities] ([id], [role_id], [user_id]) VALUES (2, 2, N'admin')
INSERT [dbo].[authorities] ([id], [role_id], [user_id]) VALUES (3, 3, N'staff001')
INSERT [dbo].[authorities] ([id], [role_id], [user_id]) VALUES (4, 3, N'staff002')
INSERT [dbo].[authorities] ([id], [role_id], [user_id]) VALUES (5, 2, N'staff002')
INSERT [dbo].[authorities] ([id], [role_id], [user_id]) VALUES (6, 4, N'userid00001')
INSERT [dbo].[authorities] ([id], [role_id], [user_id]) VALUES (7, 4, N'userid00002')
INSERT [dbo].[authorities] ([id], [role_id], [user_id]) VALUES (8, 4, N'userid00003')
SET IDENTITY_INSERT [dbo].[authorities] OFF
GO
SET IDENTITY_INSERT [dbo].[brand] ON 

INSERT [dbo].[brand] ([id], [brand]) VALUES (1, N'Me-O')
INSERT [dbo].[brand] ([id], [brand]) VALUES (2, N'Minino Yum')
INSERT [dbo].[brand] ([id], [brand]) VALUES (3, N'Whiskas')
INSERT [dbo].[brand] ([id], [brand]) VALUES (4, N'Nutrience Original Healthy Adult Indoor')
INSERT [dbo].[brand] ([id], [brand]) VALUES (5, N'Cat’s Eye')
INSERT [dbo].[brand] ([id], [brand]) VALUES (6, N'Home & Cat')
INSERT [dbo].[brand] ([id], [brand]) VALUES (7, N'Iskhanq')
INSERT [dbo].[brand] ([id], [brand]) VALUES (8, N'Nutri Source')
INSERT [dbo].[brand] ([id], [brand]) VALUES (9, N'Royal Canin')
INSERT [dbo].[brand] ([id], [brand]) VALUES (10, N'CatsRang')
INSERT [dbo].[brand] ([id], [brand]) VALUES (11, N'Zenith Puppy')
INSERT [dbo].[brand] ([id], [brand]) VALUES (12, N'Smartheart')
INSERT [dbo].[brand] ([id], [brand]) VALUES (13, N'Ganador')
INSERT [dbo].[brand] ([id], [brand]) VALUES (14, N'Classic Pets')
SET IDENTITY_INSERT [dbo].[brand] OFF
GO
SET IDENTITY_INSERT [dbo].[cart_item] ON 

INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (1, 2, 1, 12)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (2, 1, 1, 15)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (3, 1, 1, 21)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (4, 5, 1, 12)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (5, 1, 1, 16)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (6, 2, 1, 31)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (7, 2, 2, 21)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (8, 1, 2, 11)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (9, 3, 2, 33)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (10, 1, 2, 45)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (11, 9, 2, 47)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (12, 2, 2, 25)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (13, 2, 3, 21)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (14, 1, 3, 41)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (15, 3, 3, 42)
INSERT [dbo].[cart_item] ([id], [quantity], [cart_id], [product_repo_id]) VALUES (16, 1, 3, 43)
SET IDENTITY_INSERT [dbo].[cart_item] OFF
GO
SET IDENTITY_INSERT [dbo].[carts] ON 

INSERT [dbo].[carts] ([card_id], [user_id]) VALUES (1, N'userid00001')
INSERT [dbo].[carts] ([card_id], [user_id]) VALUES (2, N'userid00002')
INSERT [dbo].[carts] ([card_id], [user_id]) VALUES (3, N'userid00003')
SET IDENTITY_INSERT [dbo].[carts] OFF
GO
SET IDENTITY_INSERT [dbo].[delivery_company] ON 

INSERT [dbo].[delivery_company] ([id], [company]) VALUES (1, N'Giao hàng nhanh')
INSERT [dbo].[delivery_company] ([id], [company]) VALUES (2, N'Giao hàng tiết kiệm')
SET IDENTITY_INSERT [dbo].[delivery_company] OFF
GO
SET IDENTITY_INSERT [dbo].[imgs] ON 

INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (41, N'imgproduct0001.jpg', NULL, N'PD0001')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (42, N'imgproduct0002.jpg', NULL, N'PD0002')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (43, N'imgproduct0003.jpg', NULL, N'PD0003')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (44, N'imgproduct0004.jpg', NULL, N'PD0004')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (45, N'imgproduct0005.jpg', NULL, N'PD0005')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (46, N'imgproduct0006.jpg', NULL, N'PD0006')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (47, N'imgproduct0007.jpg', NULL, N'PD0007')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (48, N'imgproduct0008.jpg', NULL, N'PD0008')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (49, N'imgproduct0009.jpg', NULL, N'PD0009')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (50, N'imgproduct0010.jpg', NULL, N'PD0010')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (51, N'imgproduct0011.jpg', NULL, N'PD0011')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (52, N'imgproduct0012.jpg', NULL, N'PD0012')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (53, N'imgproduct0013.jpg', NULL, N'PD0013')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (54, N'imgproduct0014.jpg', NULL, N'PD0014')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (55, N'imgproduct0015.jpg', NULL, N'PD0015')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (56, N'imgproduct0016.jpg', NULL, N'PD0016')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (57, N'imgproduct0017.jpg', NULL, N'PD0017')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (58, N'imgproduct0018.jpg', NULL, N'PD0018')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (59, N'imgproduct0019.jpg', NULL, N'PD0019')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (60, N'imgproduct0020.jpg', NULL, N'PD0020')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (61, N'imgproduct0021.jpg', NULL, N'PD0021')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (62, N'imgproduct0022.jpg', NULL, N'PD0022')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (63, N'imgproduct0023.jpg', NULL, N'PD0023')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (64, N'imgproduct0024.jpg', NULL, N'PD0024')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (65, N'imgproduct0025.jpg', NULL, N'PD0025')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (66, N'imgproduct0026.jpg', NULL, N'PD0026')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (67, N'imgproduct0027.jpg', NULL, N'PD0027')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (68, N'imgproduct0028.jpg', NULL, N'PD0028')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (69, N'imgproduct0029.jpg', NULL, N'PD0029')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (70, N'imgproduct0030.jpg', NULL, N'PD0030')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (71, N'imgproduct0031.jpg', NULL, N'PD0031')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (72, N'imgproduct0032.jpg', NULL, N'PD0032')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (73, N'imgproduct0033.jpg', NULL, N'PD0033')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (74, N'imgproduct0034.jpg', NULL, N'PD0034')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (75, N'imgproduct0035.jpg', NULL, N'PD0035')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (76, N'imgproduct0036.jpg', NULL, N'PD0036')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (77, N'imgproduct0037.jpg', NULL, N'PD0037')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (78, N'imgproduct0038.jpg', NULL, N'PD0038')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (79, N'imgproduct0039.jpg', NULL, N'PD0039')
INSERT [dbo].[imgs] ([id], [name_img], [pet_id], [product_id]) VALUES (80, N'imgproduct0040.jpg', NULL, N'PD0040')
SET IDENTITY_INSERT [dbo].[imgs] OFF
GO
SET IDENTITY_INSERT [dbo].[order_detail] ON 

INSERT [dbo].[order_detail] ([id], [price], [quantity], [total], [order_id], [product_repo_id]) VALUES (1, 50000, 1, 50000, 1, 25)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [total], [order_id], [product_repo_id]) VALUES (2, 150000, 1, 150000, 1, 47)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [total], [order_id], [product_repo_id]) VALUES (3, 35000, 10, 350000, 2, 7)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [total], [order_id], [product_repo_id]) VALUES (4, 150000, 1, 150000, 2, 21)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [total], [order_id], [product_repo_id]) VALUES (5, 60000, 5, 300000, 3, 44)
INSERT [dbo].[order_detail] ([id], [price], [quantity], [total], [order_id], [product_repo_id]) VALUES (6, 300000, 1, 300000, 3, 48)
SET IDENTITY_INSERT [dbo].[order_detail] OFF
GO
SET IDENTITY_INSERT [dbo].[orders] ON 

INSERT [dbo].[orders] ([id], [create_at], [descriptions], [status], [total], [payment_id], [shipping_info_id], [user_id]) VALUES (1, CAST(N'2023-08-10T00:00:00.0000000' AS DateTime2), N'descriptions', N'Đang chờ lấy hàng', 230000, 1, 1, N'userid00003')
INSERT [dbo].[orders] ([id], [create_at], [descriptions], [status], [total], [payment_id], [shipping_info_id], [user_id]) VALUES (2, CAST(N'2023-08-19T00:00:00.0000000' AS DateTime2), N'descriptions', N'Đã nhận hàng', 530000, 1, 2, N'userid00003')
INSERT [dbo].[orders] ([id], [create_at], [descriptions], [status], [total], [payment_id], [shipping_info_id], [user_id]) VALUES (3, CAST(N'2023-08-20T00:00:00.0000000' AS DateTime2), N'descriptions', N'Đang giao hàng', 630000, 1, 3, N'userid00003')
SET IDENTITY_INSERT [dbo].[orders] OFF
GO
SET IDENTITY_INSERT [dbo].[payment] ON 

INSERT [dbo].[payment] ([id], [amount], [is_paid], [pay_at], [transaction_number], [payment_method_id]) VALUES (1, 230000, 1, CAST(N'2023-08-10T00:00:00.0000000' AS DateTime2), N'14165018', 2)
INSERT [dbo].[payment] ([id], [amount], [is_paid], [pay_at], [transaction_number], [payment_method_id]) VALUES (2, 530000, 1, CAST(N'2023-08-20T00:00:00.0000000' AS DateTime2), N'', 1)
INSERT [dbo].[payment] ([id], [amount], [is_paid], [pay_at], [transaction_number], [payment_method_id]) VALUES (3, 630000, 0, NULL, N'', 1)
SET IDENTITY_INSERT [dbo].[payment] OFF
GO
SET IDENTITY_INSERT [dbo].[payment_method] ON 

INSERT [dbo].[payment_method] ([id], [method]) VALUES (1, N'Cash')
INSERT [dbo].[payment_method] ([id], [method]) VALUES (2, N'Banking')
SET IDENTITY_INSERT [dbo].[payment_method] OFF
GO
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'AC001', N'Abyssinian cat', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'BC001', N'Bengal cat ', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'BL001', N'British longhair', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'BS001', N'British shorthair ', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'C001', N'Chihuahua', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'CCD001', N'Chinese Chongqing Dog', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'EM001', N'Egyptian mau ', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'GS001', N'German Shepherd', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'JC001', N'Japanese Chin', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'KD001', N'Kangal Dog', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'MC001', N'Maine Coon', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'ND001', N'Native dog', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'PQR001', N'Phu Quoc Ridgeback', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'SC001', N'Siamese cat ', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'SC002', N'Siberian cat', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'SF001', N'Scottish Fold ', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'SI001', N'Shiba inu', N'D001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'TB001', N'Tabby cat', N'C001')
INSERT [dbo].[pet_breed] ([breed_id], [breed_name], [type_id]) VALUES (N'TV001', N'Turkish Van', N'C001')
GO
INSERT [dbo].[pet_type] ([type_id], [type_name]) VALUES (N'B001', N'Bird')
INSERT [dbo].[pet_type] ([type_id], [type_name]) VALUES (N'C001', N'Cat')
INSERT [dbo].[pet_type] ([type_id], [type_name]) VALUES (N'D001', N'Dog')
INSERT [dbo].[pet_type] ([type_id], [type_name]) VALUES (N'M001', N'Mouse')
GO
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0001', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'ME-O Tuna In Jelly', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0002', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'The Pet FRESH Pate For Cats With Loss Of Appetite', 1, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0003', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Nekko Jelly Cat Pate', 1, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0004', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Pate Whiskas Junior For Kittens', 2, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0005', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Snappy Cat Tom Real Fish Pate', 3, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0006', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Snappy Tom Premium Pate', 4, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0007', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Zenith Adult Soft Pellets For Adult Dogs', 1, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0008', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Royal Canin X-Small Adult Dog', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0009', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Salmon Flavor', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0010', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Natural Organic Dog Food For All Ages', 2, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0011', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Kitten Ocean Fish', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0012', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Cat Mackerel Flavor', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0013', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Creamy Treat Chicken Flavor', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0014', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Creamy Treat Crab Flavor', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0015', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Creamy Treat Tuna Flavor', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0016', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Creamy Treat Bonito Flavor', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0017', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Gold Fit & Firm', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0018', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Cat Litter Lemon Scent', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0019', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Me-o Delite Tuna In Jelly', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0020', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N' Made from real fish.', 1, N'Royal Canin Indoor Adult Dry Cat Food', 9, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0021', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Apro I.Q Formula', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0022', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Zoi Dog', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0023', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Creamy Chicken & Spinach', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0024', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Nutri Pate For Poodles Of All Ages', 3, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0025', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Chicken Pate For Large Dogs', 3, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0026', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Fish Pate For All Dogs', 3, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0027', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Smartheart Puppy Power Pack', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0028', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Smartheart Adult Roast Beef Flavour', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0029', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Smartheart Small Breeds Roast Beef Flavor', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0030', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Nutrition for dogs.', 1, N'Smartheart Puppy Chicken Flavor Chunk In Gravy', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0031', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Royal Canin Hypoallergenic Dog Pate 400g', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0032', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Feedy', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0033', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Altair', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0034', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Royal Canin Poodle Adult Dog Food', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0035', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Royal Canin Maxi Adult Large Dog Food', 9, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0036', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Fresh Meat New Model - CAT', 3, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0037', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Snappy Cat Tom Tuna Mixed Fruit Pate', 1, N'CF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0038', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Pedigree Adult Mini Small Breed Adult Dog Food', 1, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0039', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Natural Organic Brown Rice, Sweet Potatoes', 1, N'DF')
INSERT [dbo].[product] ([product_id], [create_at], [product_desc], [is_active], [product_name], [brand_id], [type_id]) VALUES (N'PD0040', CAST(N'2023-10-09T15:31:34.4333333' AS DateTime2), N'Enjoy for pet', 1, N'Natural Salmon Organic', 1, N'DF')
GO
SET IDENTITY_INSERT [dbo].[product_repo] ON 

INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (1, 140000, 1, 1, 200000, 20, 500, N'PD0001')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (2, 140000, 1, 1, 200000, 20, 1000, N'PD0001')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (3, 70000, 1, 1, 100000, 50, 500, N'PD0002')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (4, 10000, 1, 1, 20000, 100, 70, N'PD0003')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (5, 30000, 1, 1, 45000, 70, 100, N'PD0004')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (6, 50000, 1, 1, 80000, 50, 400, N'PD0005')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (7, 20000, 1, 1, 35000, 100, 100, N'PD0006')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (8, 45000, 1, 1, 70000, 20, 300, N'PD0007')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (9, 14000, 1, 1, 160000, 50, 500, N'PD0008')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (10, 130000, 1, 1, 170000, 100, 200, N'PD0009')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (11, 500000, 1, 1, 600000, 20, 5000, N'PD0010')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (12, 200000, 1, 1, 250000, 70, 1000, N'PD0011')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (13, 500000, 1, 1, 600000, 20, 200, N'PD0012')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (14, 130000, 1, 1, 170000, 20, 1000, N'PD0013')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (15, 500000, 1, 1, 600000, 50, 1000, N'PD0014')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (16, 130000, 1, 1, 170000, 20, 400, N'PD0015')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (17, 200000, 1, 1, 250000, 20, 1000, N'PD0016')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (18, 45000, 1, 1, 75000, 70, 1000, N'PD0017')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (19, 200000, 1, 1, 250000, 50, 400, N'PD0018')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (20, 25000, 1, 1, 50000, 100, 200, N'PD0019')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (21, 130000, 1, 1, 150000, 20, 1000, N'PD0020')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (22, 45000, 1, 1, 75000, 20, 300, N'PD0021')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (23, 25000, 1, 1, 50000, 100, 200, N'PD0022')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (24, 200000, 1, 1, 250000, 20, 1000, N'PD0023')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (25, 25000, 1, 1, 50000, 100, 200, N'PD0024')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (26, 45000, 1, 1, 90000, 100, 1000, N'PD0025')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (27, 200000, 1, 1, 250000, 20, 200, N'PD0026')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (28, 130000, 1, 1, 150000, 70, 1000, N'PD0027')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (29, 200000, 1, 1, 250000, 100, 200, N'PD0028')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (30, 45000, 1, 1, 90000, 20, 1000, N'PD0029')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (31, 25000, 1, 1, 50000, 100, 1000, N'PD0030')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (32, 120000, 1, 1, 140000, 50, 400, N'PD0031')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (33, 130000, 1, 1, 200000, 100, 200, N'PD0032')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (34, 45000, 1, 1, 70000, 100, 200, N'PD0033')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (35, 190000, 1, 1, 230000, 70, 200, N'PD0034')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (36, 35000, 1, 1, 50000, 180, 250, N'PD0001')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (37, 70000, 1, 1, 100000, 100, 500, N'PD0001')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (38, 160000, 1, 1, 200000, 34, 500, N'PD0035')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (39, 320000, 1, 1, 400000, 73, 1000, N'PD0035')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (40, 390000, 1, 1, 560000, 72, 1200, N'PD0035')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (41, 70000, 1, 1, 90000, 83, 500, N'PD0036')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (42, 105000, 1, 1, 135000, 17, 750, N'PD0036')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (43, 15000, 1, 1, 25000, 90, 70, N'PD0037')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (44, 40000, 1, 1, 60000, 100, 400, N'PD0038')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (45, 230000, 1, 1, 280000, 35, 1000, N'PD0039')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (46, 60000, 1, 1, 75000, 40, 250, N'PD0040')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (47, 120000, 1, 1, 150000, 50, 500, N'PD0040')
INSERT [dbo].[product_repo] ([product_repo_id], [in_price], [in_stock], [is_active], [out_price], [quantity], [size], [product_id]) VALUES (48, 240000, 1, 1, 300000, 67, 1000, N'PD0040')
SET IDENTITY_INSERT [dbo].[product_repo] OFF
GO
INSERT [dbo].[product_type] ([product_type_id], [product_type_name]) VALUES (N'BF', N'Bird food')
INSERT [dbo].[product_type] ([product_type_id], [product_type_name]) VALUES (N'CF', N'Cat food')
INSERT [dbo].[product_type] ([product_type_id], [product_type_name]) VALUES (N'DF', N'Dog food')
INSERT [dbo].[product_type] ([product_type_id], [product_type_name]) VALUES (N'MF', N'Mouse food')
INSERT [dbo].[product_type] ([product_type_id], [product_type_name]) VALUES (N'RF', N'Rabbit Food')
GO
SET IDENTITY_INSERT [dbo].[recent_view] ON 

INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (1, CAST(N'2023-08-10T00:00:00.0000000' AS DateTime2), N'PD0001', N'userid00001')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (2, CAST(N'2023-08-11T00:00:00.0000000' AS DateTime2), N'PD0011', N'userid00001')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (3, CAST(N'2023-08-12T00:00:00.0000000' AS DateTime2), N'PD0013', N'userid00001')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (4, CAST(N'2023-08-13T00:00:00.0000000' AS DateTime2), N'PD0023', N'userid00001')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (5, CAST(N'2023-08-11T00:00:00.0000000' AS DateTime2), N'PD0027', N'userid00002')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (6, CAST(N'2023-08-12T00:00:00.0000000' AS DateTime2), N'PD0018', N'userid00002')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (7, CAST(N'2023-08-13T00:00:00.0000000' AS DateTime2), N'PD0017', N'userid00002')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (8, CAST(N'2023-08-14T00:00:00.0000000' AS DateTime2), N'PD0003', N'userid00002')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (9, CAST(N'2023-08-11T00:00:00.0000000' AS DateTime2), N'PD0006', N'userid00003')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (10, CAST(N'2023-08-12T00:00:00.0000000' AS DateTime2), N'PD0009', N'userid00003')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (11, CAST(N'2023-08-08T00:00:00.0000000' AS DateTime2), N'PD0016', N'userid00003')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (12, CAST(N'2023-08-02T00:00:00.0000000' AS DateTime2), N'PD0021', N'userid00003')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (13, CAST(N'2023-08-03T00:00:00.0000000' AS DateTime2), N'PD0023', N'userid00003')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (14, CAST(N'2023-08-04T00:00:00.0000000' AS DateTime2), N'PD0031', N'userid00003')
INSERT [dbo].[recent_view] ([id], [view_at], [product_id], [user_id]) VALUES (15, CAST(N'2023-08-05T00:00:00.0000000' AS DateTime2), N'PD0011', N'userid00003')
SET IDENTITY_INSERT [dbo].[recent_view] OFF
GO
SET IDENTITY_INSERT [dbo].[review] ON 

INSERT [dbo].[review] ([id], [comment], [create_at], [rate], [order_id], [product_id], [user_id]) VALUES (1, N'Sản Phẩm tốt', CAST(N'2023-08-19T00:00:00.0000000' AS DateTime2), 4, 2, N'PD0006', N'userid00003')
INSERT [dbo].[review] ([id], [comment], [create_at], [rate], [order_id], [product_id], [user_id]) VALUES (2, N'Sản Phẩm tốt giá rẻ', CAST(N'2023-08-19T00:00:00.0000000' AS DateTime2), 5, 2, N'PD0020', N'userid00003')
SET IDENTITY_INSERT [dbo].[review] OFF
GO
SET IDENTITY_INSERT [dbo].[role] ON 

INSERT [dbo].[role] ([id], [role], [role_desc]) VALUES (1, N'ROLE_SUPER_ADMIN', N'Super Admin')
INSERT [dbo].[role] ([id], [role], [role_desc]) VALUES (2, N'ROLE_ADMIN', N'Admin')
INSERT [dbo].[role] ([id], [role], [role_desc]) VALUES (3, N'ROLE_STAFF', N'Staff')
INSERT [dbo].[role] ([id], [role], [role_desc]) VALUES (4, N'ROLE_USER', N'User')
SET IDENTITY_INSERT [dbo].[role] OFF
GO
SET IDENTITY_INSERT [dbo].[search_history] ON 

INSERT [dbo].[search_history] ([id], [keyword], [search_at], [user_id]) VALUES (1, N'Cat eye', CAST(N'2023-02-05T00:00:00.0000000' AS DateTime2), N'userid00003')
INSERT [dbo].[search_history] ([id], [keyword], [search_at], [user_id]) VALUES (2, N'CatsRang', CAST(N'2023-01-02T00:00:00.0000000' AS DateTime2), N'userid00002')
INSERT [dbo].[search_history] ([id], [keyword], [search_at], [user_id]) VALUES (3, N'Home & Cat', CAST(N'2023-01-03T00:00:00.0000000' AS DateTime2), N'userid00002')
INSERT [dbo].[search_history] ([id], [keyword], [search_at], [user_id]) VALUES (4, N'Royal Canin', CAST(N'2023-02-05T00:00:00.0000000' AS DateTime2), N'userid00001')
INSERT [dbo].[search_history] ([id], [keyword], [search_at], [user_id]) VALUES (5, N'Me-O', CAST(N'2023-02-05T00:00:00.0000000' AS DateTime2), N'userid00003')
INSERT [dbo].[search_history] ([id], [keyword], [search_at], [user_id]) VALUES (6, N'Whiskas', CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'userid00003')
INSERT [dbo].[search_history] ([id], [keyword], [search_at], [user_id]) VALUES (7, N'Whistcas', CAST(N'2023-01-06T00:00:00.0000000' AS DateTime2), N'userid00003')
SET IDENTITY_INSERT [dbo].[search_history] OFF
GO
SET IDENTITY_INSERT [dbo].[shipping_info] ON 

INSERT [dbo].[shipping_info] ([id], [address], [district], [full_name], [phone], [province], [ship_fee], [ward], [delivery_company_id]) VALUES (1, N'135 Trần Hưng Đạo', N'Ninh Kiều', N'Pham Nhut Khang', N'0966456789', N'Cần Thơ', 30000, N'Phường 3', 1)
INSERT [dbo].[shipping_info] ([id], [address], [district], [full_name], [phone], [province], [ship_fee], [ward], [delivery_company_id]) VALUES (2, N'135 Trần Hưng Đạo', N'Ninh Kiều', N'Pham Nhut Khang', N'0966456789', N'Cần Thơ', 30000, N'Phường 3', 2)
INSERT [dbo].[shipping_info] ([id], [address], [district], [full_name], [phone], [province], [ship_fee], [ward], [delivery_company_id]) VALUES (3, N'135 Trần Hưng Đạo', N'Ninh Kiều', N'Phạm Trung Đức', N'0966456789', N'Cần Thơ', 30000, N'Phường 5', 1)
INSERT [dbo].[shipping_info] ([id], [address], [district], [full_name], [phone], [province], [ship_fee], [ward], [delivery_company_id]) VALUES (4, N'135 Trần Hưng Đạo', N'Ninh Kiều', N'Phạm Trung Đức', N'0966456789', N'Cần Thơ', 30000, N'Phường 5', 2)
INSERT [dbo].[shipping_info] ([id], [address], [district], [full_name], [phone], [province], [ship_fee], [ward], [delivery_company_id]) VALUES (5, N'135 Trần Hưng Đạo', N'Ninh Kiều', N'Nguyễn Quốc Duy', N'0966456789', N'Cần Thơ', 30000, N'Phường 1', 1)
INSERT [dbo].[shipping_info] ([id], [address], [district], [full_name], [phone], [province], [ship_fee], [ward], [delivery_company_id]) VALUES (6, N'135 Trần Hưng Đạo', N'Ninh Kiều', N'Nguyễn Quốc Duy', N'0966456789', N'Cần Thơ', 30000, N'Phường 1', 2)
INSERT [dbo].[shipping_info] ([id], [address], [district], [full_name], [phone], [province], [ship_fee], [ward], [delivery_company_id]) VALUES (7, N'135 Trần Hưng Đạo', N'Ninh Kiều', N'Ngô Thị Hồng Hạnh', N'0966456789', N'Cần Thơ', 30000, N'Phường 3', 1)
INSERT [dbo].[shipping_info] ([id], [address], [district], [full_name], [phone], [province], [ship_fee], [ward], [delivery_company_id]) VALUES (8, N'135 Trần Hưng Đạo', N'Ninh Kiều', N'Nguyễn Minh Anh', N'0966456789', N'Cần Thơ', 30000, N'Phường 3', 2)
INSERT [dbo].[shipping_info] ([id], [address], [district], [full_name], [phone], [province], [ship_fee], [ward], [delivery_company_id]) VALUES (9, N'135 Trần Hưng Đạo', N'Ninh Kiều', N'Trần Văn Việt', N'0966456789', N'Cần Thơ', 30000, N'Phường 3', 1)
SET IDENTITY_INSERT [dbo].[shipping_info] OFF
GO
INSERT [dbo].[users] ([user_id], [avatar], [birthday], [create_at], [email], [fullname], [gender], [is_active], [is_email_verified], [password], [phone], [token], [token_create_at], [username]) VALUES (N'admin', N'avatar0001.jpg', CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'admin@gmail.com', N'Admin 1', 0, 1, 1, N'passnaylachuoi32kytuduocmahoa', N'0912543876', N'chonaydeghitoken', CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'admin')
INSERT [dbo].[users] ([user_id], [avatar], [birthday], [create_at], [email], [fullname], [gender], [is_active], [is_email_verified], [password], [phone], [token], [token_create_at], [username]) VALUES (N'staff001', N'avatar0001.jpg', CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'staff001@gmail.com', N'Staff 001', 0, 1, 1, N'passnaylachuoi32kytuduocmahoa', N'0912543876', N'chonaydeghitoken', CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'staff001')
INSERT [dbo].[users] ([user_id], [avatar], [birthday], [create_at], [email], [fullname], [gender], [is_active], [is_email_verified], [password], [phone], [token], [token_create_at], [username]) VALUES (N'staff002', N'avatar0001.jpg', CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'staff002@gmail.com', N'Staff 001', 0, 1, 1, N'passnaylachuoi32kytuduocmahoa', N'0912543876', N'chonaydeghitoken', CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'staff002')
INSERT [dbo].[users] ([user_id], [avatar], [birthday], [create_at], [email], [fullname], [gender], [is_active], [is_email_verified], [password], [phone], [token], [token_create_at], [username]) VALUES (N'superadmin', N'avatar0001.jpg', CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'superadmin@gmail.com', N'Super Admin', 0, 1, 1, N'passnaylachuoi32kytuduocmahoa', N'0912543876', N'chonaydeghitoken', CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'superadmin')
INSERT [dbo].[users] ([user_id], [avatar], [birthday], [create_at], [email], [fullname], [gender], [is_active], [is_email_verified], [password], [phone], [token], [token_create_at], [username]) VALUES (N'userid00001', N'avatar0001.jpg', CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'userid0001@gmail.com', N'Nguyen Thi Hong', 0, 1, 1, N'passnaylachuoi32kytuduocmahoa', N'0912543876', N'chonaydeghitoken', CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'khach001')
INSERT [dbo].[users] ([user_id], [avatar], [birthday], [create_at], [email], [fullname], [gender], [is_active], [is_email_verified], [password], [phone], [token], [token_create_at], [username]) VALUES (N'userid00002', N'avatar0002.jpg', CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'userid0002@gmail.com', N'Nguyen Van Viet', 0, 1, 1, N'passnaylachuoi32kytuduocmahoa', N'0912543876', N'chonaydeghitoken', CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'khach002')
INSERT [dbo].[users] ([user_id], [avatar], [birthday], [create_at], [email], [fullname], [gender], [is_active], [is_email_verified], [password], [phone], [token], [token_create_at], [username]) VALUES (N'userid00003', N'avatar0003.jpg', CAST(N'1999-01-01T00:00:00.0000000' AS DateTime2), CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'userid0001@gmail.com', N'Ho Tung Mau', 0, 1, 1, N'passnaylachuoi32kytuduocmahoa', N'0912543876', N'chonaydeghitoken', CAST(N'2023-01-05T00:00:00.0000000' AS DateTime2), N'khach003')
GO
ALTER TABLE [dbo].[addresses]  WITH CHECK ADD  CONSTRAINT [FK1fa36y2oqhao3wgg2rw1pi459] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[addresses] CHECK CONSTRAINT [FK1fa36y2oqhao3wgg2rw1pi459]
GO
ALTER TABLE [dbo].[adopt]  WITH CHECK ADD  CONSTRAINT [FKfxjyhqtn4y0x8j5cvoojv8xgn] FOREIGN KEY([pet_id])
REFERENCES [dbo].[pet] ([pet_id])
GO
ALTER TABLE [dbo].[adopt] CHECK CONSTRAINT [FKfxjyhqtn4y0x8j5cvoojv8xgn]
GO
ALTER TABLE [dbo].[adopt]  WITH CHECK ADD  CONSTRAINT [FKnux0ry9kbu4uq290t1pbd64sx] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[adopt] CHECK CONSTRAINT [FKnux0ry9kbu4uq290t1pbd64sx]
GO
ALTER TABLE [dbo].[authorities]  WITH CHECK ADD  CONSTRAINT [FKk91upmbueyim93v469wj7b2qh] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[authorities] CHECK CONSTRAINT [FKk91upmbueyim93v469wj7b2qh]
GO
ALTER TABLE [dbo].[authorities]  WITH CHECK ADD  CONSTRAINT [FKlcdevx7gs0kv62awadnvy4nle] FOREIGN KEY([role_id])
REFERENCES [dbo].[role] ([id])
GO
ALTER TABLE [dbo].[authorities] CHECK CONSTRAINT [FKlcdevx7gs0kv62awadnvy4nle]
GO
ALTER TABLE [dbo].[cart_item]  WITH CHECK ADD  CONSTRAINT [FKi43ekc29q06meofcvkre1n8ge] FOREIGN KEY([product_repo_id])
REFERENCES [dbo].[product_repo] ([product_repo_id])
GO
ALTER TABLE [dbo].[cart_item] CHECK CONSTRAINT [FKi43ekc29q06meofcvkre1n8ge]
GO
ALTER TABLE [dbo].[cart_item]  WITH CHECK ADD  CONSTRAINT [FKlqwuo55w1gm4779xcu3t4wnrd] FOREIGN KEY([cart_id])
REFERENCES [dbo].[carts] ([card_id])
GO
ALTER TABLE [dbo].[cart_item] CHECK CONSTRAINT [FKlqwuo55w1gm4779xcu3t4wnrd]
GO
ALTER TABLE [dbo].[carts]  WITH CHECK ADD  CONSTRAINT [FKb5o626f86h46m4s7ms6ginnop] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[carts] CHECK CONSTRAINT [FKb5o626f86h46m4s7ms6ginnop]
GO
ALTER TABLE [dbo].[favorite]  WITH CHECK ADD  CONSTRAINT [FKa2lwa7bjrnbti5v12mga2et1y] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[favorite] CHECK CONSTRAINT [FKa2lwa7bjrnbti5v12mga2et1y]
GO
ALTER TABLE [dbo].[favorite]  WITH CHECK ADD  CONSTRAINT [FKfcbqhey8aaypcl38aumjxoo1r] FOREIGN KEY([pet_id])
REFERENCES [dbo].[pet] ([pet_id])
GO
ALTER TABLE [dbo].[favorite] CHECK CONSTRAINT [FKfcbqhey8aaypcl38aumjxoo1r]
GO
ALTER TABLE [dbo].[imgs]  WITH CHECK ADD  CONSTRAINT [FK9gq0rbrwgqkr4uuvdplgu24yk] FOREIGN KEY([pet_id])
REFERENCES [dbo].[pet] ([pet_id])
GO
ALTER TABLE [dbo].[imgs] CHECK CONSTRAINT [FK9gq0rbrwgqkr4uuvdplgu24yk]
GO
ALTER TABLE [dbo].[imgs]  WITH CHECK ADD  CONSTRAINT [FKrfp4668jvev5qivrkn5wukarp] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([product_id])
GO
ALTER TABLE [dbo].[imgs] CHECK CONSTRAINT [FKrfp4668jvev5qivrkn5wukarp]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FK3fgut2docr6cu4kxl9re7u48c] FOREIGN KEY([product_repo_id])
REFERENCES [dbo].[product_repo] ([product_repo_id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FK3fgut2docr6cu4kxl9re7u48c]
GO
ALTER TABLE [dbo].[order_detail]  WITH CHECK ADD  CONSTRAINT [FKrws2q0si6oyd6il8gqe2aennc] FOREIGN KEY([order_id])
REFERENCES [dbo].[orders] ([id])
GO
ALTER TABLE [dbo].[order_detail] CHECK CONSTRAINT [FKrws2q0si6oyd6il8gqe2aennc]
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [FK32ql8ubntj5uh44ph9659tiih] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [FK32ql8ubntj5uh44ph9659tiih]
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [FKag8ppnkjvx255gj7lm3m18wkj] FOREIGN KEY([payment_id])
REFERENCES [dbo].[payment] ([id])
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [FKag8ppnkjvx255gj7lm3m18wkj]
GO
ALTER TABLE [dbo].[orders]  WITH CHECK ADD  CONSTRAINT [FKrvd32bftxff2tuj4ka0mt6m86] FOREIGN KEY([shipping_info_id])
REFERENCES [dbo].[shipping_info] ([id])
GO
ALTER TABLE [dbo].[orders] CHECK CONSTRAINT [FKrvd32bftxff2tuj4ka0mt6m86]
GO
ALTER TABLE [dbo].[payment]  WITH CHECK ADD  CONSTRAINT [FKjii2n6db6cje3km5ybsbgo8cl] FOREIGN KEY([payment_method_id])
REFERENCES [dbo].[payment_method] ([id])
GO
ALTER TABLE [dbo].[payment] CHECK CONSTRAINT [FKjii2n6db6cje3km5ybsbgo8cl]
GO
ALTER TABLE [dbo].[pet]  WITH CHECK ADD  CONSTRAINT [FK9u4gps42602t8n0xelpsjq3j7] FOREIGN KEY([breed_id])
REFERENCES [dbo].[pet_breed] ([breed_id])
GO
ALTER TABLE [dbo].[pet] CHECK CONSTRAINT [FK9u4gps42602t8n0xelpsjq3j7]
GO
ALTER TABLE [dbo].[pet_breed]  WITH CHECK ADD  CONSTRAINT [FK3mxu9mx8i6mu6h5goj1oe6fnx] FOREIGN KEY([type_id])
REFERENCES [dbo].[pet_type] ([type_id])
GO
ALTER TABLE [dbo].[pet_breed] CHECK CONSTRAINT [FK3mxu9mx8i6mu6h5goj1oe6fnx]
GO
ALTER TABLE [dbo].[price_change]  WITH CHECK ADD  CONSTRAINT [FK4fyar728wapv8suk868v26rbl] FOREIGN KEY([product_repo_id])
REFERENCES [dbo].[product_repo] ([product_repo_id])
GO
ALTER TABLE [dbo].[price_change] CHECK CONSTRAINT [FK4fyar728wapv8suk868v26rbl]
GO
ALTER TABLE [dbo].[price_change]  WITH CHECK ADD  CONSTRAINT [FKdttuc6yfbaebesku0nuc8xlrh] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[price_change] CHECK CONSTRAINT [FKdttuc6yfbaebesku0nuc8xlrh]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FKajjopj7ffr42w11bav8gut0cp] FOREIGN KEY([type_id])
REFERENCES [dbo].[product_type] ([product_type_id])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FKajjopj7ffr42w11bav8gut0cp]
GO
ALTER TABLE [dbo].[product]  WITH CHECK ADD  CONSTRAINT [FKs6cydsualtsrprvlf2bb3lcam] FOREIGN KEY([brand_id])
REFERENCES [dbo].[brand] ([id])
GO
ALTER TABLE [dbo].[product] CHECK CONSTRAINT [FKs6cydsualtsrprvlf2bb3lcam]
GO
ALTER TABLE [dbo].[product_repo]  WITH CHECK ADD  CONSTRAINT [FKfjicl5fnhwa8ji0d7cbxaxefb] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([product_id])
GO
ALTER TABLE [dbo].[product_repo] CHECK CONSTRAINT [FKfjicl5fnhwa8ji0d7cbxaxefb]
GO
ALTER TABLE [dbo].[recent_view]  WITH CHECK ADD  CONSTRAINT [FK1odviskd2inncsyvf46kfquh2] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([product_id])
GO
ALTER TABLE [dbo].[recent_view] CHECK CONSTRAINT [FK1odviskd2inncsyvf46kfquh2]
GO
ALTER TABLE [dbo].[recent_view]  WITH CHECK ADD  CONSTRAINT [FKg9l7vfuf3g796unremiso164u] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[recent_view] CHECK CONSTRAINT [FKg9l7vfuf3g796unremiso164u]
GO
ALTER TABLE [dbo].[review]  WITH CHECK ADD  CONSTRAINT [FK6cpw2nlklblpvc7hyt7ko6v3e] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[review] CHECK CONSTRAINT [FK6cpw2nlklblpvc7hyt7ko6v3e]
GO
ALTER TABLE [dbo].[review]  WITH CHECK ADD  CONSTRAINT [FKiyof1sindb9qiqr9o8npj8klt] FOREIGN KEY([product_id])
REFERENCES [dbo].[product] ([product_id])
GO
ALTER TABLE [dbo].[review] CHECK CONSTRAINT [FKiyof1sindb9qiqr9o8npj8klt]
GO
ALTER TABLE [dbo].[review]  WITH CHECK ADD  CONSTRAINT [FKnkc5s3da46cbx8oeqrfhnm7es] FOREIGN KEY([order_id])
REFERENCES [dbo].[orders] ([id])
GO
ALTER TABLE [dbo].[review] CHECK CONSTRAINT [FKnkc5s3da46cbx8oeqrfhnm7es]
GO
ALTER TABLE [dbo].[search_history]  WITH CHECK ADD  CONSTRAINT [FK8ll2cxj6i83mnrcyxrxl4b7dm] FOREIGN KEY([user_id])
REFERENCES [dbo].[users] ([user_id])
GO
ALTER TABLE [dbo].[search_history] CHECK CONSTRAINT [FK8ll2cxj6i83mnrcyxrxl4b7dm]
GO
ALTER TABLE [dbo].[shipping_info]  WITH CHECK ADD  CONSTRAINT [FKcdv7sqawgeuecmd5h0v0yqtka] FOREIGN KEY([delivery_company_id])
REFERENCES [dbo].[delivery_company] ([id])
GO
ALTER TABLE [dbo].[shipping_info] CHECK CONSTRAINT [FKcdv7sqawgeuecmd5h0v0yqtka]
GO
USE [master]
GO
ALTER DATABASE [petfoster] SET  READ_WRITE 
GO
