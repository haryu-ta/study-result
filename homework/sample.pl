#!/usr/bin/perl

use strict;
use warnings;

#print
print "Hello World\n";

print "===================変数宣言==========================\n";
#変数宣言(数値)
my $str = 3.5;

#計算
print $str * 2 . "\n";

print "===================配列宣言==========================\n";
#配列宣言
my @arr = ('ryoei','kana','haruka','ryuta');

#表示
print "$arr[0]\n";
print "$arr[1]\n";
print "$arr[2]\n";
print "$arr[$#arr]\n";
print "全要素⇒@arr\n";
my $arrcnt = @arr;
print "要素数:$arrcnt\n";

#最後尾に追加
push(@arr,"yukari");
print "@arr\n";
#最前部に追加
unshift(@arr,"masaaki");
print "@arr\n";
#最後部から削除
pop(@arr);
print "@arr\n";
#最前部から削除
shift(@arr);
print "@arr\n";

print "===================クオート構文==========================\n";
print("@arr[0,1]\n");
@arr[0,3] = @arr[3,0]; #要素入替
print("@arr\n");

print "===================多次元配列===========================\n";
my @arrs = (
	['0','1','2'],
	['3','4','5']
);
print "@{$arrs[0]}\n";

my $val1;
my $val2;

foreach $val1 (@arrs){
	foreach $val2 (@{$val1}){
		print "$val2\t";
	}
	print "\n";
}

print "===================連想配列===========================\n";

my %fruits = ("red" => "apple","yellow"=> "pineapple", "orange","mango" );
print "$fruits{'red'}\n";

while ( my ($key,$val) = each %fruits ){
	print "$key = $val\n";
};

print "========\n";
my @color = ( 'red','yellow' );
print "@fruits{@color}\n";

print "===================制御文==========================\n";
my $val3 = "b";
if($val3 eq "a"){
	print "一致\n";
}elsif($val3 ne "a"){
	print "一致しない\n";
}

my $val4 = 5;
do{
	print "カウントダウン:$val4\n";
	$val4--;
}while( $val4 >= 0);

my $val5;
for( $val5 = $val4 ;$val5 <= 10 ; $val5++ ){
	print "カウントアップ:$val5\n";
}

my @val6 = (1,2,3,4,5,6);
my $cnt;
foreach $cnt (@val6){
	if ( $cnt == 3 ){
		next;
	}elsif( $cnt == 5 ){
		last;
	}
	print "ループ:$cnt\n";
}
