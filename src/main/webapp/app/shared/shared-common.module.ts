import { LOCALE_ID, NgModule } from '@angular/core';
import { Title } from '@angular/platform-browser';

import {
    StatisticalOperationsExternalSharedLibsModule,
    AutocompleteComponent,
    AutocompleteShortListComponent,
    AutocompleteLongListComponent,
    AutocompleteEnumComponent,
    AutofocusDirective,
    HelpTooltipComponent,
    JhiAlertErrorComponent,
    JhiLanguageHelper,
    MakeFixedRoomDirective,
    OrderListComponent,
    PaginationComponent,
    SpinnerComponent,
    StepsComponent,
    StickyTableHeaderDirective,
    TriInputSwitchComponent,
    CurrencyComponent
} from '.';

import { ChartComponent } from './chart/chart.component';

@NgModule({
    imports: [
        StatisticalOperationsExternalSharedLibsModule
    ],
    declarations: [
        JhiAlertErrorComponent,
        StickyTableHeaderDirective,
        TriInputSwitchComponent,
        MakeFixedRoomDirective,
        AutocompleteComponent,
        AutocompleteShortListComponent,
        AutocompleteLongListComponent,
        AutocompleteEnumComponent,
        ChartComponent,
        OrderListComponent,
        HelpTooltipComponent,
        PaginationComponent,
        AutofocusDirective,
        CurrencyComponent,
        SpinnerComponent,
        StepsComponent,
    ],
    providers: [
        JhiLanguageHelper,
        Title,
        {
            provide: LOCALE_ID,
            useValue: 'es'
        },
    ],
    exports: [
        StatisticalOperationsExternalSharedLibsModule,
        JhiAlertErrorComponent,
        StickyTableHeaderDirective,
        TriInputSwitchComponent,
        MakeFixedRoomDirective,
        AutocompleteComponent,
        AutocompleteShortListComponent,
        AutocompleteLongListComponent,
        AutocompleteEnumComponent,
        ChartComponent,
        OrderListComponent,
        HelpTooltipComponent,
        PaginationComponent,
        AutofocusDirective,
        CurrencyComponent,
        SpinnerComponent,
        StepsComponent,
    ]
})
export class StatisticalOperationsExternalSharedCommonModule { }
