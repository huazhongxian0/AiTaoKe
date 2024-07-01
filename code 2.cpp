#include <iostream>
using namespace std;
const int N = 1000000;
int m;
int e[N], r[N], l[N];
int idx;

void init()
{
	l[1] = 0,r[0] = 1;
	idx = 2;
}
void add(int k, int x)
{
	e[idx] = x;
	r[idx] = r[k];
	l[idx] = k;
	l[r[k]] = idx;
	r[k] = idx;
	idx ++; 
}
void remove(int k)
{
	r[l[k]]=r[k];
	l[r[k]]=l[k];
}
int main()
{
	cin >> m;
	init();
	while(m--)
	{
	int k,x;
	string s;
	cin >> s;
	if(s == "L")
	
		cin >> x;
		add(0, x);
	}
	else if(s == "R")
	{
		cin >> x;
		add(l[1], x);
	}
	else if(s == "D")
	{
		cin >> k;
		remove(k + 1);
	}
	else if(s == "IL")
	{
		cin >> k >> x;
		add(l[k+1], x);
	}
	else
	{
		cin >> k >> x;
		add(k + 1, x);
	}
	}
	for(int i = r[0]; i!= 1; i= r[i])
	{
		cout << e[i] << ' ';
	}
	return 0;
}














