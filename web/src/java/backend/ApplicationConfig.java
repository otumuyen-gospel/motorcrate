/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package backend;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author user1
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method.
     * It is automatically populated with
     * all resources defined in the project.
     * If required, comment out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(backend.AccountResource.class);
        resources.add(backend.ActivateResource.class);
        resources.add(backend.AnalyticsResource.class);
        resources.add(backend.Authenticate.class);
        resources.add(backend.CloseResource.class);
        resources.add(backend.CouponResource.class);
        resources.add(backend.CreatePlanResource.class);
        resources.add(backend.DeleteResource.class);
        resources.add(backend.EditorResource.class);
        resources.add(backend.FaqResource.class);
        resources.add(backend.InvoiceResource.class);
        resources.add(backend.MessengerResource.class);
        resources.add(backend.MoreResource.class);
        resources.add(backend.OperationResource.class);
        resources.add(backend.PrivacyResource.class);
        resources.add(backend.ProfileResource.class);
        resources.add(backend.ReviewResource.class);
        resources.add(backend.ScheduleResource.class);
        resources.add(backend.SearchResource.class);
        resources.add(backend.StripeKeyResource.class);
        resources.add(backend.TermsResource.class);
    }
    
}
