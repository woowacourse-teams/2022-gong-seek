import { HashTagSearchResponseType } from '@/api/hashTag/hashTagType';
import { ArticleSearchResponseType } from '@/api/search/searchType';
import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

export const getUserSearchResult = async ({
	target,
	cursorId,
	searchIndex,
}: {
	target: string;
	cursorId: string;
	searchIndex: string;
}) => {
	const encodedTarget = encodeURIComponent(target);
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<ArticleSearchResponseType>(
		`/api/articles/search/author?author=${encodedTarget}&cursorId=${cursorId}&size=6`,
	);
	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId:
			data.articles && data.articles[data.articles.length - 1]
				? String(data.articles[data.articles.length - 1].id)
				: '',
		target: target,
		searchIndex,
	};
};

export const getArticleSearchResult = async ({
	target,
	cursorId,
	searchIndex,
}: {
	target: string;
	cursorId: string;
	searchIndex: string;
}) => {
	const encodedTarget = encodeURIComponent(target);
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<ArticleSearchResponseType>(
		`/api/articles/search/text?text=${encodedTarget}&cursorId=${cursorId}&size=6`,
	);

	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId:
			data.articles && data.articles[data.articles.length - 1]
				? String(data.articles[data.articles.length - 1].id)
				: '',
		target: target,
		searchIndex,
	};
};

export const getArticleByHashTag = async ({
	hashTags,
	cursorId = '',
}: {
	hashTags: string;
	cursorId: string;
}) => {
	const encodedTarget = encodeURIComponent(hashTags);
	const axiosInstance = generateAxiosInstanceWithAccessToken();
	const { data } = await axiosInstance.get<HashTagSearchResponseType>(
		`/api/articles/search/tags?tagsText=${encodedTarget}&cursorId=${cursorId}&size=6`,
	);
	return {
		articles: data.articles,
		hasNext: data.hasNext,
		cursorId:
			data.articles && data.articles[data.articles.length - 1]
				? String(data.articles[data.articles.length - 1].id)
				: '',
		hashTags: hashTags,
	};
};

export const getSearchResult = async ({
	target,
	searchIndex,
	cursorId = '',
}: {
	target: string;
	searchIndex: string;
	cursorId: string;
}) => {
	if (searchIndex === '유저') {
		const data = await getUserSearchResult({ target, searchIndex, cursorId });
		return data;
	}

	const data = await getArticleSearchResult({ target, searchIndex, cursorId });
	return data;
};
