#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main()
{
    int secret_num, guess_num, num_guesses = 0;
    srand(time(NULL)); 
    secret_num = rand() % 100 + 1; 

    printf("����һ����������Ϸ\n");
    printf("�Ҹ����������Ǵ�1��100��.\n");
    printf("����ʮ�λ������.\n\n");

    while (num_guesses < 10)
    {
        printf("Guess #%d: ", num_guesses + 1);
        scanf("%d", &guess_num);
        num_guesses++;

        if (guess_num < secret_num)
            printf("̫���ˣ�����!\n");
        else if (guess_num > secret_num)
            printf("̫���ˣ�����!\n");
        else
        {
            printf("��ϲ��³�����ȷ�𰸣������� %d .\n", num_guesses);
            return 0;
        }
    }

    printf("��Ǹ�����е��̣��𰸴��������ȷ���� %d.\n", secret_num);
    return 0;
}

