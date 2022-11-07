import { ArticleTotalType } from '@/api/article/articleType';

export interface HashTagResponseType {
	tag: string[];
}

export type SingleHashTagSearchItemType = Omit<
	ArticleTotalType,
	'updatedAt' | 'isAuthor' | 'hasVote'
>;

export interface HashTagSearchResponseType {
	articles: SingleHashTagSearchItemType[];
	hasNext: boolean;
}

export interface InfiniteHashTagSearchResponseType extends HashTagSearchResponseType {
	cursorId: string;
	hashTags: string;
}
