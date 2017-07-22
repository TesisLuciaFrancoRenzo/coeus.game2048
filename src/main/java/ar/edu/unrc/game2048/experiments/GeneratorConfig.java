/*
 * Copyright (C) 2016  Lucia Bressan <lucyluz333@gmial.com>,
 *                     Franco Pellegrini <francogpellegrini@gmail.com>,
 *                     Renzo Bianchini <renzobianchini85@gmail.com
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package ar.edu.unrc.game2048.experiments;

import ar.edu.unrc.game2048.experiments.configurations.EncogConfiguration2048;
import ar.edu.unrc.game2048.experiments.configurations.LearningExperiment;
import ar.edu.unrc.game2048.experiments.configurations.ntuples.*;
import ar.edu.unrc.game2048.experiments.configurations.perceptrons.ConfigPerceptronNTupleLinearSimplified_512;
import ar.edu.unrc.game2048.experiments.configurations.perceptrons.ConfigPerceptronNTupleTanHSimplified_512;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Configuración de un generador de Tests.
 *
 * @author lucia bressan, franco pellegrini, renzo bianchini
 */
public
class GeneratorConfig {

    /**
     *
     */
    private static final int NO_ANNEALING = -1;
    private final double  alpha;
    private final int     annealingAlpha;
    private final boolean canCollectStatistics;
    private final String  className;
    private final int     eligibilityTraceLength;
    private final Double  explorationRate;
    private final Double  explorationRateFinalValue;
    private final Integer explorationRateFinishInterpolation;
    private final Double  explorationRateInitialValue;
    private final Integer explorationRateStartInterpolation;
    private final double  gamma;
    private final double  lambda;
    private final int     number;
    private final int     repetitions;
    private final boolean replaceTraces;
    private final double  whenStartToExplore;

    /**
     * @param className
     * @param alpha
     * @param annealingAlpha
     * @param lambda
     * @param gamma
     * @param explorationRate
     * @param explorationRateInitialValue
     * @param replaceTraces
     * @param explorationRateFinalValue
     * @param number
     * @param explorationRateStartInterpolation
     * @param explorationRateFinishInterpolation
     */
    public
    GeneratorConfig(
            final int repetitions,
            final boolean canCollectStatistics,
            final String className,
            final double alpha,
            final int annealingAlpha,
            final double lambda,
            final int eligibilityTraceLength,
            final Double gamma,
            final double whenStartToExplore,
            final Double explorationRate,
            final Double explorationRateInitialValue,
            final Double explorationRateFinalValue,
            final Integer explorationRateStartInterpolation,
            final Integer explorationRateFinishInterpolation,
            final boolean replaceTraces,
            final int number
    ) {
        super();
        this.repetitions = repetitions;
        this.canCollectStatistics = canCollectStatistics;
        this.alpha = alpha;
        this.annealingAlpha = annealingAlpha;
        this.lambda = lambda;
        this.eligibilityTraceLength = eligibilityTraceLength;
        this.gamma = gamma;
        this.whenStartToExplore = whenStartToExplore;
        this.explorationRate = explorationRate;
        this.explorationRateFinishInterpolation = explorationRateFinishInterpolation;
        this.replaceTraces = replaceTraces;
        this.number = number;
        this.explorationRateInitialValue = explorationRateInitialValue;
        this.explorationRateFinalValue = explorationRateFinalValue;
        this.explorationRateStartInterpolation = explorationRateStartInterpolation;
        this.className = className;
    }

    /**
     * @param numberForShow
     * @param experiment
     * @param statisticsOnly
     * @param runStatisticsForBackups
     * @param createLogs
     * @param lambda
     * @param alpha
     * @param annealingAlpha
     * @param gamma
     * @param gamesToPlay
     * @param saveEvery
     * @param saveBackupEvery
     * @param gamesToPlayPerThreadForStatistics
     * @param tileToWinForStatistics
     * @param simulationsForStatistics
     * @param explorationRate
     * @param explorationRateInitialValue
     * @param explorationRateFinalValue
     * @param explorationRateStartInterpolation
     * @param explorationRateFinishInterpolation
     * @param filePath
     * @param concurrentLayer
     */
    protected static
    void configAndExecute(
            final boolean canCollectStatistics,
            final int numberForShow,
            final LearningExperiment experiment,
            final boolean statisticsOnly,
            final boolean runStatisticsForBackups,
            final boolean createLogs,
            final double lambda,
            final int eligibilityTraceLength,
            final double alpha,
            final int annealingAlpha,
            final double gamma,
            final int gamesToPlay,
            final int saveEvery,
            final int saveBackupEvery,
            final int gamesToPlayPerThreadForStatistics,
            final int tileToWinForStatistics,
            final int simulationsForStatistics,
            final double whenStartToExplore,
            final Double explorationRate,
            final Double explorationRateInitialValue,
            final Double explorationRateFinalValue,
            final Integer explorationRateStartInterpolation,
            final Integer explorationRateFinishInterpolation,
            final String filePath,
            final boolean[] concurrentLayer,
            final boolean replaceEligibilityTraces,
            final boolean concurrencyInComputeBestPossibleAction
    ) {
        experiment.setCanCollectStatistics(canCollectStatistics);
        experiment.setStatisticsOnly(statisticsOnly);
        experiment.setRunStatisticsForBackups(runStatisticsForBackups);
        experiment.createLogs(createLogs);
        experiment.setLambda(lambda);
        experiment.setEligibilityTraceLength(eligibilityTraceLength);
        experiment.setReplaceEligibilityTraces(replaceEligibilityTraces);
        experiment.setGamma(gamma);
        final double[] alphas = { alpha, alpha };
        experiment.setAlpha(alphas);
        experiment.setWhenStartToExplore(whenStartToExplore);
        if ( explorationRate != null ) {
            experiment.setExplorationRateToFixed(explorationRate);
        } else {
            experiment.setExplorationRate(explorationRateInitialValue,
                    explorationRateStartInterpolation,
                    explorationRateFinalValue,
                    explorationRateFinishInterpolation);
        }
        experiment.setInitializePerceptronRandomized(false);
        experiment.setConcurrencyInComputeBestPossibleAction(concurrencyInComputeBestPossibleAction);
        experiment.setConcurrencyInLayer(concurrentLayer);
        experiment.setTileToWinForStatistics(tileToWinForStatistics);
        if ( annealingAlpha == NO_ANNEALING ) {
            experiment.setLearningRateAdaptationToFixed();
        } else {
            experiment.setLearningRateAdaptationToAnnealing(annealingAlpha);
        }
        experiment.setGamesToPlay(gamesToPlay);
        experiment.setSaveEvery(saveEvery);
        experiment.setSaveBackupEvery(saveBackupEvery);
        experiment.setGamesToPlayPerThreadForStatistics(gamesToPlayPerThreadForStatistics);
        experiment.setSimulationsForStatistics(simulationsForStatistics);
        experiment.setExportToExcel(true);
        System.out.println("*=*=*=*=*=*=*=*=*=*=* N" + numberForShow + " Ejecutando " + filePath + " *=*=*=*=*=*=*=*=*=*=*");
        experiment.start(numberForShow, filePath, true, null, false);
    }

    /**
     * @param ex error a tratar.
     *
     * @return traducción de la traza de errores a texto.
     */
    public static
    String getMsj( final Throwable ex ) {
        final StringWriter sw = new StringWriter();
        final PrintWriter  pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        return sw.toString();
    }

    protected static
    void runOneConfig(
            final String experimentDirName,
            final boolean statisticsOnly,
            final boolean runStatisticsForBackups,
            final boolean createLogs,
            final int gamesToPlay,
            final int saveEvery,
            final int saveBackupEvery,
            final int gamesToPlayPerThreadForStatistics,
            final int tileToWinForStatistics,
            final int simulationsForStatistics,
            final List< Double > explorationRateList,
            final String filePath,
            final boolean[] concurrentLayer,
            final GeneratorConfig expConfig,
            final boolean concurrencyInComputeBestPossibleAction
    ) {
        try {
            final String explorationRateString = "-w" + expConfig.getWhenStartToExplore() + ( ( explorationRateList != null )
                                                                                              ? ( "-explorationRate_" +
                                                                                                  expConfig.getExplorationRate() )
                                                                                              : "-explorationRate_" +
                                                                                                expConfig.getExplorationRateInitialValue() + '_' +
                                                                                                expConfig.getExplorationRateFinalValue() + '_' +
                                                                                                expConfig.getExplorationRateStartInterpolation() +
                                                                                                '_' +
                                                                                                expConfig.getExplorationRateFinishInterpolation() );
            final String newFilePath = filePath + experimentDirName + File.separator + expConfig.getRepetitions() + "-alpha_" + expConfig.getAlpha() +
                                       ( ( expConfig.getAnnealingAlpha() > 0 ) ? ( "-anneal_" + expConfig.getAnnealingAlpha() ) : "" ) + "-lambda_" +
                                       expConfig.getLambda() + "-gamma_" + expConfig.getGamma() + explorationRateString + "-replaceTraces_" +
                                       expConfig.isReplaceTraces() + File.separator;
            final File newPath = new File(newFilePath);
            if ( !newPath.exists() ) {
                newPath.mkdirs();
            }

            final Constructor< ? > classConstructor;
            Object[]               classParameters = null;
            switch ( expConfig.getClassName() ) {
                case "ConfigNTupleBasicLinear_512":
                    classConstructor = ConfigNTupleBasicLinear_512.class.getConstructor();
                    break;
                case "ConfigNTupleBasicLinear_32768":
                    classConstructor = ConfigNTupleBasicLinear_32768.class.getConstructor();
                    break;
                case "ConfigNTupleBasicLinearSimplified_512":
                    classConstructor = ConfigNTupleBasicLinearSimplified_512.class.getConstructor();
                    break;
                case "ConfigNTupleBasicSigmoid_32768":
                    classConstructor = ConfigNTupleBasicSigmoid_32768.class.getConstructor();
                    break;
                case "ConfigNTupleBasicTanH_32768":
                    classConstructor = ConfigNTupleBasicTanH_32768.class.getConstructor();
                    break;
                case "ConfigNTupleBasicTanHSimplified_512":
                    classConstructor = ConfigNTupleBasicTanHSimplified_512.class.getConstructor();
                    break;
                case "ConfigNTupleSymmetricLinear_32768":
                    classConstructor = ConfigNTupleSymmetricLinear_32768.class.getConstructor();
                    break;
                case "ConfigNTupleSymmetricTanH_32768":
                    classConstructor = ConfigNTupleSymmetricTanH_32768.class.getConstructor();
                    break;
                case "ConfigPerceptronNTupleLinearSimplified_noBias_512":
                    classConstructor = ConfigPerceptronNTupleLinearSimplified_512.class.getConstructor(EncogConfiguration2048.PARAMETER_TYPE);
                    classParameters = new Object[] { false };
                    break;
                case "ConfigPerceptronNTupleLinearSimplified_withBias_512":
                    classConstructor = ConfigPerceptronNTupleLinearSimplified_512.class.getConstructor(EncogConfiguration2048.PARAMETER_TYPE);
                    classParameters = new Object[] { true };
                    break;
                case "ConfigPerceptronNTupleTanHSimplified_noBias_512":
                    classConstructor = ConfigPerceptronNTupleTanHSimplified_512.class.getConstructor(EncogConfiguration2048.PARAMETER_TYPE);
                    classParameters = new Object[] { false };
                    break;
                case "ConfigPerceptronNTupleTanHSimplified_withBias_512":
                    classConstructor = ConfigPerceptronNTupleTanHSimplified_512.class.getConstructor(EncogConfiguration2048.PARAMETER_TYPE);
                    classParameters = new Object[] { true };
                    break;

                default:
                    throw new IllegalArgumentException("no se reconoce la clase: " + expConfig.getClassName());
            }

            final LearningExperiment cloneExperiment = (LearningExperiment) ( ( classParameters != null )
                                                                              ? classConstructor.newInstance(classParameters)
                                                                              : classConstructor.newInstance() );
            cloneExperiment.setExperimentName(expConfig.getClassName());
            configAndExecute(expConfig.isCanCollectStatistics(),
                    expConfig.getNumber(),
                    cloneExperiment,
                    statisticsOnly,
                    runStatisticsForBackups,
                    createLogs,
                    expConfig.getLambda(),
                    expConfig.getEligibilityTraceLength(),
                    expConfig.getAlpha(),
                    expConfig.getAnnealingAlpha(),
                    expConfig.getGamma(),
                    gamesToPlay,
                    saveEvery,
                    saveBackupEvery,
                    gamesToPlayPerThreadForStatistics,
                    tileToWinForStatistics,
                    simulationsForStatistics,
                    expConfig.getWhenStartToExplore(),
                    expConfig.getExplorationRate(),
                    expConfig.getExplorationRateInitialValue(),
                    expConfig.getExplorationRateFinalValue(),
                    expConfig.getExplorationRateStartInterpolation(),
                    expConfig.getExplorationRateFinishInterpolation(),
                    newFilePath,
                    concurrentLayer,
                    expConfig.isReplaceTraces(),
                    concurrencyInComputeBestPossibleAction);
        } catch ( NoSuchMethodException | ClassCastException | InstantiationException | IllegalAccessException | IllegalArgumentException |
                InvocationTargetException ex ) {
            Logger.getLogger(TestGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * @return alpha
     */
    public
    double getAlpha() {
        return alpha;
    }

    /**
     * @return annealingAlpha
     */
    public
    int getAnnealingAlpha() {
        return annealingAlpha;
    }

    /**
     * @return
     */
    public
    String getClassName() {
        return className;
    }

    public
    int getEligibilityTraceLength() {
        return eligibilityTraceLength;
    }

    /**
     * @return explorationRate
     */
    public
    Double getExplorationRate() {
        return explorationRate;
    }

    /**
     * @return the explorationRateFinalValue
     */
    public
    Double getExplorationRateFinalValue() {
        return explorationRateFinalValue;
    }

    /**
     * @return the explorationRateFinishInterpolation
     */
    public
    Integer getExplorationRateFinishInterpolation() {
        return explorationRateFinishInterpolation;
    }

    /**
     * @return the explorationRateInitialValue
     */
    public
    Double getExplorationRateInitialValue() {
        return explorationRateInitialValue;
    }

    /**
     * @return the explorationRateStartInterpolation
     */
    public
    Integer getExplorationRateStartInterpolation() {
        return explorationRateStartInterpolation;
    }

    /**
     * @return gamma
     */
    public
    double getGamma() {
        return gamma;
    }

    /**
     * @return lambda
     */
    public
    double getLambda() {
        return lambda;
    }

    /**
     * @return number
     */
    public
    int getNumber() {
        return number;
    }

    public
    int getRepetitions() {
        return repetitions;
    }

    public
    double getWhenStartToExplore() {
        return whenStartToExplore;
    }

    public
    boolean isCanCollectStatistics() {
        return canCollectStatistics;
    }

    /**
     * @return replaceTraces
     */
    public
    boolean isReplaceTraces() {
        return replaceTraces;
    }

}
