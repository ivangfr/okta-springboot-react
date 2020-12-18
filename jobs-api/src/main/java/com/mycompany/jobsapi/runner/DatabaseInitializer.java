package com.mycompany.jobsapi.runner;

import com.mycompany.jobsapi.model.Job;
import com.mycompany.jobsapi.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Slf4j
@RequiredArgsConstructor
@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final JobService jobService;

    @Value("${app.number-of-fake-jobs:0}")
    private int numberOfFakeJobs;

    @Override
    public void run(String... args) {
        log.info("Number of fake jobs to be created: {}", numberOfFakeJobs);
        if (numberOfFakeJobs <= 0) {
            return;
        }

        if (!jobService.getNewestJobs(1).isEmpty()) {
            log.info("Database has already data!");
            return;
        }

        log.info("Starting creating jobs ...");
        for (int i = 0; i < numberOfFakeJobs; i++) {
            String tech = TECHS.get(random.nextInt(TECHS.size()));
            String level = LEVELS.get(random.nextInt(LEVELS.size()));
            String levelName = level.split(SPLIT_CHAR)[0];
            String levelYears = level.split(SPLIT_CHAR)[1];
            String area = AREAS.get(random.nextInt(AREAS.size()));
            String company = COMPANIES.get(random.nextInt(COMPANIES.size()));
            String companyName = company.split(SPLIT_CHAR)[0];
            String companyLogoUrl = company.split(SPLIT_CHAR)[1];
            String location = LOCATIONS.get(random.nextInt(LOCATIONS.size()));
            String moreInfo = MORE_INFO.get(random.nextInt(MORE_INFO.size()));

            Job job = new Job();
            job.setTitle(String.format(TITLE_TEMPLATE, levelName, tech, area));
            job.setCompany(companyName);
            job.setLogoUrl(companyLogoUrl);
            job.setDescription(
                    String.format(DESCRIPTION_TEMPLATE, companyName, levelName, tech, levelYears, location, moreInfo));
            jobService.saveJob(job);

            log.info("Job created! => {}", job);
        }
        log.info("Created {} jobs successfully!", numberOfFakeJobs);
    }

    private static final Random random = new SecureRandom();

    private static final String SPLIT_CHAR = ";";
    private static final List<String> TECHS = Arrays.asList("Java", "C", "Python", "React", "Angular", "Scala");
    private static final List<String> LEVELS = Arrays.asList("Junior;2", "Mid-career;4", "Senior;6", "Expert;8");
    private static final List<String> AREAS = Arrays.asList("Finance", "Cloud", "Back-office",
            "Artificial Intelligence", "Mobile");
    private static final List<String> COMPANIES = Arrays.asList(
            "Google;https://upload.wikimedia.org/wikipedia/commons/thumb/2/2f/Google_2015_logo.svg/500px-Google_2015_logo.svg.png",
            "Facebook;https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Facebook_Logo_%282019%29.svg/500px-Facebook_Logo_%282019%29.svg.png",
            "Yahoo;https://upload.wikimedia.org/wikipedia/commons/thumb/3/3a/Yahoo%21_%282019%29.svg/420px-Yahoo%21_%282019%29.svg.png",
            "Microsoft;https://upload.wikimedia.org/wikipedia/commons/thumb/9/96/Microsoft_logo_%282012%29.svg/440px-Microsoft_logo_%282012%29.svg.png",
            "Takeaway;https://upload.wikimedia.org/wikipedia/de/1/1d/Takeaway.com_logo.svg",
            "Oracle;https://upload.wikimedia.org/wikipedia/commons/thumb/c/c3/Oracle_Logo.svg/440px-Oracle_Logo.svg.png");
    private static final List<String> LOCATIONS = Arrays.asList("Berlin/Germany", "New York City/US", "Porto/Portugal",
            "Sao Paulo/Brazil");
    private static final String TITLE_TEMPLATE = "%s %s Developer - %s";
    private static final String DESCRIPTION_TEMPLATE = "We at %s are looking for a %s %s Developer with around %s years of experience. The candidate must have Bachelor, Master or PhD in Computer Science. The position is for our office in %s. If you are looking for new challenges every day, has good communication skills and are fluent in English, you are the exact candidate we are looking for.%s";
    private static final List<String> MORE_INFO = Arrays.asList("",
            "\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam at nunc sed elit interdum sodales. Curabitur interdum est quis ex molestie, ac viverra eros pellentesque. Sed dolor nibh, efficitur gravida mauris nec, molestie dapibus lacus. Phasellus eget turpis in arcu lacinia volutpat vitae ac est. Maecenas imperdiet nisl velit, eget consequat ipsum porta a. Nunc eu risus enim. Morbi bibendum neque nec massa convallis, at maximus neque ullamcorper. Quisque venenatis ante diam, vel tincidunt urna pulvinar et. Donec tincidunt in diam eget ullamcorper. Mauris enim tellus, sollicitudin ut volutpat ut, finibus nec libero. Sed sodales ultrices metus. Mauris id nibh nec ante feugiat accumsan. Donec eget congue sapien. Proin vel nulla eu nunc facilisis varius non quis ipsum. Suspendisse malesuada eros nec odio placerat commodo.",
            "\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam at nunc sed elit interdum sodales. Curabitur interdum est quis ex molestie, ac viverra eros pellentesque. Sed dolor nibh, efficitur gravida mauris nec, molestie dapibus lacus. Phasellus eget turpis in arcu lacinia volutpat vitae ac est. Maecenas imperdiet nisl velit, eget consequat ipsum porta a. Nunc eu risus enim. Morbi bibendum neque nec massa convallis, at maximus neque ullamcorper. Quisque venenatis ante diam, vel tincidunt urna pulvinar et. Donec tincidunt in diam eget ullamcorper. Mauris enim tellus, sollicitudin ut volutpat ut, finibus nec libero. Sed sodales ultrices metus. Mauris id nibh nec ante feugiat accumsan. Donec eget congue sapien. Proin vel nulla eu nunc facilisis varius non quis ipsum. Suspendisse malesuada eros nec odio placerat commodo.\n\nMauris id eros porta elit gravida interdum a a massa. Donec turpis libero, commodo sed erat eget, mattis volutpat arcu. Sed tortor tellus, viverra ac cursus sit amet, ullamcorper eget ligula. Pellentesque laoreet metus sit amet dolor euismod aliquam. Nulla a ante felis. In vitae mollis dolor. In vitae felis tortor. Phasellus felis ligula, fringilla a nunc sit amet, tempor tristique arcu. Nullam dictum non leo eu pharetra. Pellentesque ac ligula eros.",
            "\n\nLorem ipsum dolor sit amet, consectetur adipiscing elit. Nullam at nunc sed elit interdum sodales. Curabitur interdum est quis ex molestie, ac viverra eros pellentesque. Sed dolor nibh, efficitur gravida mauris nec, molestie dapibus lacus. Phasellus eget turpis in arcu lacinia volutpat vitae ac est. Maecenas imperdiet nisl velit, eget consequat ipsum porta a. Nunc eu risus enim. Morbi bibendum neque nec massa convallis, at maximus neque ullamcorper. Quisque venenatis ante diam, vel tincidunt urna pulvinar et. Donec tincidunt in diam eget ullamcorper. Mauris enim tellus, sollicitudin ut volutpat ut, finibus nec libero. Sed sodales ultrices metus. Mauris id nibh nec ante feugiat accumsan. Donec eget congue sapien. Proin vel nulla eu nunc facilisis varius non quis ipsum. Suspendisse malesuada eros nec odio placerat commodo.\n\nMauris id eros porta elit gravida interdum a a massa. Donec turpis libero, commodo sed erat eget, mattis volutpat arcu. Sed tortor tellus, viverra ac cursus sit amet, ullamcorper eget ligula. Pellentesque laoreet metus sit amet dolor euismod aliquam. Nulla a ante felis. In vitae mollis dolor. In vitae felis tortor. Phasellus felis ligula, fringilla a nunc sit amet, tempor tristique arcu. Nullam dictum non leo eu pharetra. Pellentesque ac ligula eros.\n\nDuis bibendum felis in velit consectetur vestibulum vitae at augue. Donec sed bibendum felis. Suspendisse tincidunt molestie nunc vehicula ornare. Aliquam fringilla sem ligula, a dignissim purus eleifend id. Suspendisse blandit gravida porta. Nam interdum non tellus at efficitur. Fusce feugiat sed lorem ut mollis. Vestibulum tempor turpis vitae efficitur vehicula. In sapien tellus, sollicitudin id dui ut, pretium ornare quam.");
}