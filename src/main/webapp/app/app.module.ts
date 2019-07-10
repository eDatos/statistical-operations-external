import './vendor.ts';

import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { BrowserModule } from '@angular/platform-browser';
import { Ng2Webstorage } from 'ng2-webstorage';

import { customHttpProvider } from './blocks/interceptor/http.provider';
import { PaginationConfig } from './blocks/config/uib-pagination.config';
import { StatisticalOperationsExternalConfigModule } from './config/config.module';
import { StatisticalOperationsExternalInterfacesModule } from './interfaces/interfaces.module';
import { JhiMainComponent, LayoutRoutingModule, ErrorComponent, notFoundRoute, NavbarComponent, FooterComponent } from './layouts';
import { StatisticalOperationsExternalSharedModule } from './shared';

const APP_ROUTES = [
    notFoundRoute
]

@NgModule({
    imports: [
        BrowserModule,
        LayoutRoutingModule,
        Ng2Webstorage.forRoot({ prefix: 'jhi', separator: '-' }),

        StatisticalOperationsExternalConfigModule,
        StatisticalOperationsExternalInterfacesModule,
        StatisticalOperationsExternalSharedModule,

        // jhipster-needle-angular-add-module JHipster will add new module here
        RouterModule.forRoot(APP_ROUTES, { useHash: true })
    ],
    declarations: [
        JhiMainComponent,
        ErrorComponent,
        NavbarComponent,
        FooterComponent
    ],
    providers: [
        customHttpProvider(),
        PaginationConfig,
    ],
    bootstrap: [JhiMainComponent]
})
export class StatisticalOperationsExternalAppModule { }
