import { PropsWithStrictChildren } from 'gongseek-types';
import { InfiniteQueryObserverResult } from 'react-query';

import InfiniteScrollObserver, {
	ObserverResponseType,
} from '@/components/common/InfiniteScrollObserver/InfiniteScrollObserver';
import * as S from '@/components/common/ResponsiveInfiniteCardList/ResponsiveInfiniteCardList.styles';

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
