import { PropsWithStrictChildren } from 'gongseek-types';
import { InfiniteQueryObserverResult } from 'react-query';

import * as S from '@/components/@common/ResponsiveInfiniteCardList/ResponsiveInfiniteCardList.styles';
import InfiniteScrollObserver, {
	ObserverResponseType,
} from '@/components/@helper/observer/InfiniteScrollObserver';

export interface ResponsiveInfiniteCardListProps<DataType> {
	hasNext: boolean;
	fetchNextPage: () => Promise<InfiniteQueryObserverResult<DataType, Error>>;
}

const ResponsiveInfiniteCardList = <DataType extends ObserverResponseType = ObserverResponseType>({
	hasNext,
	fetchNextPage,
	children,
}: PropsWithStrictChildren<ResponsiveInfiniteCardListProps<DataType>>) => (
	<InfiniteScrollObserver hasNext={hasNext} fetchNextPage={fetchNextPage}>
		<S.Container>{children}</S.Container>
	</InfiniteScrollObserver>
);

export default ResponsiveInfiniteCardList;
