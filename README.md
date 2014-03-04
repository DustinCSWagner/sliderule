Project: sliderule
------------------

A simple hack to generate a circular sliderule 

### Welcome
Feel free to browse the source. You can run the .jar file to create the slide rule in [.svg](https://github.com/DustinCSWagner/sliderule/blob/master/slideRuler.svg?raw=true) and [.pdf](https://github.com/DustinCSWagner/sliderule/blob/master/slideRuler.pdf?raw=true), or just download them from the root of the project directory. This project uses Batik for rendering. Its uploaded as an eclipse project. 

### Execute the jar
Run the .jar if you would like to generate new .svg and .pdf files. 
```
$ java -jar sliderule.jar
```
Thanks for looking, the instructions below are also provided in a .odc file in the docs directory. 

-Dustin

The Slide Rule
=====

## An Introduction

Slide rules are mechanical analog computers. Before the
proliferation of the personal computer, dealing with large numbers was
difficult. Slide rules made getting an approximate answer a whole lot easier,
and approximate answers were usually "good enough" in most situations.
Slide rules are primarily used to give rough estimates of
multiplication and division problems, but can also do fancier things like
square roots, cube roots, unit conversion, trigonometry, and a ton of
specialized functions for architects, engineers, astronomers, and finance
people. Slide rules usually look like a narrow ruler, but
for today's demonstration we will be building a circular slide rule.

![](https://raw.github.com/DustinCSWagner/sliderule/master/docs/image00.png "A Pickett N600-ES Slide Rule")

A Pickett N600-ES Slide Rule

![](https://raw.github.com/DustinCSWagner/sliderule/master/docs/image01.png "A Gilson Atlas Calculator Circular Slide Rule")

A Gilson Atlas Calculator Circular Slide Rule

## Some History

1614 - Discovery of logarithms by John Napier, a Scottish landowner known as a
mathematician, physicist, and astronomer.

1622 - Invention of the slide rule by William Oughtred, an English
mathematician. William is also the same person who introduced "x" as the
multiplication symbol.

1859 - The modern form of the slide rule was created by Amedee Mannheim, a
French artillery lieutenant at the time.

1976 - The final slide rule manufactured by Keuffel &amp; Esser (K&amp;E) was donated
to the Smithsonian Institute in Washington, DC.

Although the last K&amp;E slide ruler marked an end of an era, slide rules are
still manufactured today. They are cheap and durable calculation tools still
used among pilots, heating and air conditioning technicians, and machinists.

Slide rulers can be easily bought in auctions for about $5. However a slide
rule that traveled with Apollo 11 to the moon, Buzz Aldrin's Pickett N600-ES,
recently auctioned for over $75,000.

## Why Slide Rules Work

Slide rules work by allowing you to add or subtract numbers on scales to
perform multiplication, division, or other operations. This works because
logarithms are exponents. Although you don't need to understand everything
about exponents and logarithms to use a slide ruler, you should know that in
order to multiply two numbers, you add their exponents; to divide you subtract
the exponents.

For Example: 2^2 x 2^3 = 2^5 = 32  and 2^5 / 2^2 = 2^3 = 8

The "C" and "D" scales on a slide rule use base 10 logarithms to scale the
distance between the marks. The statement LOG(1) =0 is the same as saying
10^0=1 and LOG(2)=0.301 is the same as 10^0.301=2.

If this is over your head, don't worry about it - you can learn more about
this later. For now, you should concentrate on how to use a slide rule.

## A Slide Rule Joke

Did you hear about the engineer who made a bed in the shape of a slide rule?

He slept like a log!

## Build a Slide Rule

First construct the slide rule by cutting it out and gluing it to construction
paper or paper plates. The smaller "C" scale should fit inside of the larger
"D" scale. Attach the rings together with a paper clip, small rivet, or
eyelet. Notice that the scales have numbers spaced the same distance apart by
lining up the 1's.

![](https://raw.github.com/DustinCSWagner/sliderule/master/docs/image02.png)

## Use a Slide Rule

To perform multiplication, align the first multiplier to the 1 on the D scale,
find the second multiplier on the D scale, and read the product off of C. Let
us compute 1 x 2 = 2. Align 2 on C to match 1 on D. Look around the scale, do
you see that 2x2=4, 2x3=6, and 2x4=8? Notice that 2 x 1.5 =
3, and that 2*6 = 1.2 when that happens we must move the decimal place one,
2x6=12.

![](https://raw.github.com/DustinCSWagner/sliderule/master/docs/image03.png)

To perform division, align the denominator (the one on bottom) on the C scale
to the 1 on the D scale. The numerator is on the C scale, and the answer is
read off of D. For example, align 3 on C to 1 on D. Notice that 9 / 3 = 3 and
1.2 (a.k.a. 12) / 3 = 4.

![](https://raw.github.com/DustinCSWagner/sliderule/master/docs/image04.png)

Now that we have the basics down, you can work the following problems or you
can create your own multiplication and division problems. Remember that you
will only be able to get 2 or 3 significant digits out of this tool, but that
will usually be close enough!

2 x 1 =

2 x 3 =

4 x 5 =

5 x 5 =

2.2 x 2 =

22 x 2 =

4.1 x 3 =

3.1 x 2.3 =

7.3 x 1.5 =

95 x 7.3 =


Answers: 2, 6, 20, 25, 4.4, 44, 12.3, 7.13, 10.95, 693.5


9 / 3 =

12 / 3 =

20 / 5 =

25 / 5 =

2.5 / 5 =

1.2 / 3 =

4.1 / 1.2 =

88 / 2.3 =

73 / 1.5 =

87 / 3 =


Answers: 3, 4, 4, 5, 0.5, 0.4, 3.4, 38.3, 48.7, 29
