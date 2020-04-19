%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%
%%        BReAst: Sujétate a la vida
%%
%%                                                   Ángela Abreu Iglesias
%%                                                   annabreuig@gmail.com
%%                                                   March 2020
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%
%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%

%Read data file
clear
close all
a=importdata('Resultados_Medias_Mes.txt', ' ');
b=cell2mat(strfind(a,';')); %Id, date, time and values are separated with a ';' character

%Initial values
ENABLE_GET_MONTH = 1;
ENABLE_GET_YEAR = 0;
ENABLE_GET_WEEK = 0;
[sensorValuesMatrix, dateValuesMatrix] = ObtainData(a, b, ENABLE_GET_MONTH, ENABLE_GET_YEAR, ENABLE_GET_WEEK);
meanSensorValuesPerMonth=sensorValuesMatrix;

%Plot one heat map per month
ENABLE_PLOT_EACH_SENSOR_MAP = 0;
PlotGraphs(meanSensorValuesPerMonth, dateValuesMatrix, ENABLE_PLOT_EACH_SENSOR_MAP, ENABLE_GET_MONTH, ENABLE_GET_YEAR, ENABLE_GET_WEEK)