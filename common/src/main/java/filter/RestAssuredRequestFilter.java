package filter;

import io.restassured.filter.Filter;
import io.restassured.filter.FilterContext;
import io.restassured.response.Response;
import io.restassured.specification.FilterableRequestSpecification;
import io.restassured.specification.FilterableResponseSpecification;
import utility.Logger;

import static constatnt.Constants.BLANK;
import static java.lang.String.format;

public class RestAssuredRequestFilter implements Filter {

    private static final Logger logger = Logger.getInstance();

    @Override
    public Response filter(
            FilterableRequestSpecification requestSpec,
            FilterableResponseSpecification responseSpec,
            FilterContext ctx
    ) {
        Response response = ctx.next(requestSpec, responseSpec);
        if (response.statusCode() != 200) {
            logger.error(format(
                    "%s%s%s => %d%s%s",
                    requestSpec.getMethod(),
                    BLANK,
                    requestSpec.getURI(),
                    response.getStatusCode(),
                    BLANK,
                    response.getStatusLine()
                    ));
        }
        logger.info(format(
                "%s%s%s \n Request Body => %s\n Response Status => %d%s%s \n Response Body => %s",
                requestSpec.getMethod(),
                BLANK,
                requestSpec.getURI(),
                requestSpec.getBody(),
                response.getStatusCode(),
                BLANK,
                response.getStatusLine(),
                response.getBody().prettyPrint()
        ));
        return response;
    }
}
