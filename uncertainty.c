#include <math.h>
#include <stdio.h>
#include <stdlib.h>

// c���Կ���̨�Ĳ�ȷ���ȼ�����
// �������������Ǹ�javaFX�����޹أ�ֻ�Ƿ�������

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
	printf("���������ݵĸ���:");
	int amount;
	scanf("%d", &amount);

	printf("���������ݣ�\n");
	double *data = (double *)malloc(amount * sizeof(double));
	for (int i = 0; i < amount; i++)
		scanf("%lf", &data[i]);

	printf("�����������Ĳ�ȷ���ȣ�");
	double instrument;
	scanf("%lf", &instrument);

	double data_mean = mean(data, amount);
	printf("��ֵΪ��%lf\n", data_mean);
	double SD = std_deviation(data, data_mean, amount);
	printf("������ı�׼��Ϊ��%lf\n", SD);
	double A_uncertainty = type_A_uncertainty(SD, amount);
	printf("ƽ��ֵ�ı�׼��Ϊ��%lf\n", A_uncertainty);
	printf("A�಻ȷ����Ϊ��%lf\n", A_uncertainty);
	double B_uncertainty = type_B_uncertainty(instrument);
	printf("B�಻ȷ����Ϊ��%lf\n", B_uncertainty);
	double uncertain = uncertainty(A_uncertainty, B_uncertainty);
	printf("�ϳɲ�ȷ����Ϊ��%lf\n", uncertain);
	printf("��Բ�ȷ����Ϊ��%lf%%\n", 100*relative_uncertainty(uncertain, data_mean));
	printf("���������ʾΪ��%lf+-%lf\n", data_mean, uncertain);
	free(data);
	system("pause>nul");
}
