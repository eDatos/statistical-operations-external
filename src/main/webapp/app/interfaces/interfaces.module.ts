import { CUSTOM_ELEMENTS_SCHEMA, NgModule } from '@angular/core';
import { TitleBarComponent } from './title-bar/title-bar.component';
import { StatisticalOperationsExternalSharedModule } from '../shared';
import { RouterModule } from '@angular/router';
import { lugarRoute, LugarComponent } from './lugar';
import { StatisticalOperationsExternalDocumentoModule } from '../documento/documento.module';

const ENTITY_STATES = [
    ...lugarRoute
];

@NgModule({
    imports: [
        StatisticalOperationsExternalSharedModule,
        StatisticalOperationsExternalDocumentoModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TitleBarComponent,
        LugarComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class StatisticalOperationsExternalInterfacesModule {}
