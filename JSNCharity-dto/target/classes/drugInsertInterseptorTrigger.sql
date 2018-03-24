
SET ANSI_NULLS ON
GO
SET QUOTED_IDENTIFIER ON
GO
-- =============================================
-- Author:		Balaji Gold
-- Create date: 24-03-2018
-- Description:	Aggrigate Quantiy column of similar rows
-- =============================================
CREATE TRIGGER AgrigativeTrigger 
   ON  drug
   INSTEAD OF INSERT
AS 
BEGIN
	-- SET NOCOUNT ON added to prevent extra result sets from
	-- interfering with SELECT statements.
	SET NOCOUNT ON;

	DECLARE @userName varchar(255),@drugName varchar(255),@batchNo int,@expiryDate date,@quntity int,@unitPrice float;

	SET @userName = (SELECT userName from inserted);
	SET @drugName = (SELECT drugName from inserted);
	SET @batchNo = (SELECT batchNo from inserted);
	SET @expiryDate = (SELECT expiryDate from inserted);
	SET @quntity = (SELECT quantity from inserted);
	SET @unitPrice = (SELECT unitPrice from inserted);
		
	SELECT * INTO #tempData FROM drug WHERE drug.userName= @userName AND drug.drugName= @drugName AND
	drug.expiryDate = @expiryDate AND drug.unitPrice = @unitPrice;
	IF (SELECT COUNT(*) FROM #tempData) > 0
	BEGIN
		UPDATE drug SET quantity = (@quntity + (Select quantity from #tempData)) WHERE 
		drug.userName= @userName AND drug.drugName= @drugName AND drug.batchNo = (Select batchNo from #tempData);
    END
	ELSE
	BEGIN
		INSERT INTO drug (userName,drugName,batchNo,expiryDate,quantity,unitPrice) values (@userName,@drugName,@batchNo,@expiryDate,@quntity,@unitPrice);
	END
	DROP TABLE #tempData;
END
GO
