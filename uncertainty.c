#include <math.h>
#include <stdio.h>
#include <stdlib.h>

// c语言控制台的不确定度计算器
// 本程序和上面的那个javaFX程序无关，只是放在这里

double mean(double data[],int amount)
{
	double sum = 0;
	for (int i = 0; i < amount; i++)
		sum += data[i];
	return sum / amount;
}

double std_deviation(double data[], double mean, int amount)
{
	if (amount == 1)
		return 0;
	double temp = 0;
	for (int i = 0; i < amount; i++)
		temp += pow(data[i] - mean, 2);
	double Bessle = sqrt(temp / (amount - 1));
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
	return Bessle * t;
}

double type_A_uncertainty(double SD, int amount)
{
	return SD / sqrt(amount);
}

double type_B_uncertainty(double instrument_uncertainty)
{
	return instrument_uncertainty / sqrt(3);
}

double uncertainty(double A_uncertainty, double B_uncertainty)
{
	double squA = pow(A_uncertainty, 2);
	double squB = pow(B_uncertainty, 2);
	return sqrt(squA + squB);
}

double relative_uncertainty(double uncertainty, double mean)
{
	return uncertainty / mean;
}

int main()
{
	printf("请输入数据的个数:");
	int amount;
	scanf("%d", &amount);

	printf("请输入数据：\n");
	double *data = (double *)malloc(amount * sizeof(double));
	for (int i = 0; i < amount; i++)
		scanf("%lf", &data[i]);

	printf("请输入仪器的不确定度：");
	double instrument;
	scanf("%lf", &instrument);

	double data_mean = mean(data, amount);
	printf("均值为：%lf\n", data_mean);
	double SD = std_deviation(data, data_mean, amount);
	printf("修正后的标准差为：%lf\n", SD);
	double A_uncertainty = type_A_uncertainty(SD, amount);
	printf("平均值的标准差为：%lf\n", A_uncertainty);
	printf("A类不确定度为：%lf\n", A_uncertainty);
	double B_uncertainty = type_B_uncertainty(instrument);
	printf("B类不确定度为：%lf\n", B_uncertainty);
	double uncertain = uncertainty(A_uncertainty, B_uncertainty);
	printf("合成不确定度为：%lf\n", uncertain);
	printf("相对不确定度为：%lf%%\n", 100*relative_uncertainty(uncertain, data_mean));
	printf("测量结果表示为：%lf+-%lf\n", data_mean, uncertain);
	free(data);
	system("pause>nul");
}
