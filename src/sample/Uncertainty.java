package sample;

class Uncertainty
{
    private double dataMean;
    double getDataMean(){return dataMean;}

    private double sD;
    double getSD(){return sD;}

    private double aUncertainty;
    double getAUncertainty(){return aUncertainty;}

    private double bUncertainty;
    double getBUncertainty(){return bUncertainty;}

    private double uncertain;
    double getUncertain(){return uncertain;}

    private double relativeUncertainty;
    double getRelativeUncertainty(){return relativeUncertainty;}

    private String measureResult;
    String getMeasureResult(){return measureResult;}

    Uncertainty(double instrument,double ...data)
    {
        // 计算平均值
        int amount=data.length;
        double sum = 0;
        for (int i = 0; i < amount; i++)
            sum += data[i];
        dataMean = sum / amount;
        // 计算标准差
        if (amount == 1)
            sD = 0;
        else
        {
            double temp = 0;
            for (int i = 0; i < amount; i++)
                temp += Math.pow(data[i] - dataMean, 2);
            double bessle = Math.sqrt(temp / (amount - 1));
            double t;
            switch (amount)
            {
                case 2:
                    t = 1.84; break;
                case 3:
                    t = 1.32; break;
                case 4:
                    t = 1.2; break;
                case 5:
                    t = 1.14; break;
                default:
                    t = 1; break;
            }
            sD = bessle * t;
        }
        // 计算A类不确定度
        aUncertainty = sD / Math.sqrt(amount);
        // 计算B类不确定度
        bUncertainty = instrument / Math.sqrt(3);
        // 计算合成不确定度
        double squA = Math.pow(aUncertainty, 2);
        double squB = Math.pow(bUncertainty, 2);
        uncertain = Math.sqrt(squA + squB);
        // 计算相对不确定度
        relativeUncertainty = uncertain / dataMean;
        // 得到表达结果
        measureResult = String.format("%f+-%f",dataMean,uncertain);
    }
}