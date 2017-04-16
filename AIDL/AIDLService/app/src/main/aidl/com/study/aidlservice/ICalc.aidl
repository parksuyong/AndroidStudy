// ICalc.aidl
package com.study.aidlservice;

// Declare any non-default types here with import statements

interface ICalc {
   int Add(in int a, in int b);
   	int Mul(in int a, in int b);
   	int Sub(in int a, in int b);
   	int Div(in int a, in int b);
}
