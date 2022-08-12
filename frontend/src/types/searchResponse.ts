import { CommonArticleType } from '@/types/articleResponse';

export interface SearchResultType {
	articles: CommonArticleType[];
	hasNext: boolean;
}

export interface InfiniteSearchResultType extends SearchResultType {
	cursorId: string;
	target: string;
}
