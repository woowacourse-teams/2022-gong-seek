import { ArticleTotalType } from '@/api/article/articleType';

export interface SearchResultType {
	articles: Omit<ArticleTotalType, 'updatedAt' | 'hasVote' | 'isAuthor'>[];
	hasNext: boolean;
}

export interface InfiniteSearchResultType extends SearchResultType {
	cursorId: string;
	target: string;
	searchIndex: string;
}

export interface InfiniteHashTagSearchResultType extends SearchResultType {
	cursorId: string;
	hashTags: string;
}
