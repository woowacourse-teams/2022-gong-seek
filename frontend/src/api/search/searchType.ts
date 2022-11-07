import { ArticleTotalType } from '@/api/article/articleType';

export interface ArticleSearchResponseType {
	articles: Omit<ArticleTotalType, 'updatedAt' | 'hasVote' | 'isAuthor'>[];
	hasNext: boolean;
}

export interface InfiniteArticleSearchResponseType extends ArticleSearchResponseType {
	cursorId: string;
	target: string;
	searchIndex: string;
}
