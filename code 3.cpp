#include <stdio.h>
#include <stdlib.h>
#include <time.h>

int main()
{
    int secret_num, guess_num, num_guesses = 0;
    srand(time(NULL)); 
    secret_num = rand() % 100 + 1; 

    printf("这是一个猜数字游戏\n");
    printf("我给出的数字是从1到100的.\n");
    printf("你有十次机会猜它.\n\n");

    while (num_guesses < 10)
    {
        printf("Guess #%d: ", num_guesses + 1);
        scanf("%d", &guess_num);
        num_guesses++;

        if (guess_num < secret_num)
            printf("太低了，笨笨!\n");
        else if (guess_num > secret_num)
            printf("太高了，笨笨!\n");
        else
        {
            printf("恭喜你猜出了正确答案，它就是 %d .\n", num_guesses);
            return 0;
        }
    }

    printf("抱歉哈，有点捞，答案错误哈。正确答案是 %d.\n", secret_num);
    return 0;
}

