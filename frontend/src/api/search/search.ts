import { HashTagSearchResponseType } from '@/api/hashTag/hashTagType';
import { ArticleSearchResponseType } from '@/api/search/searchType';
import { generateAxiosInstanceWithAccessToken } from '@/utils/generateAxiosInstance';

interface getUserSearchResultProps {
	target: string;
	cursorId: string;
	searchIndex: string;
}

export const getUserSearchResult = async ({
	target,
	cursorId,
	searchIndex,
}: getUserSearchResultProps) => {
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

interface getArticleSearchResultProps {
	target: string;
	cursorId: string;
	searchIndex: string;
}

export const getArticleSearchResult = async ({
	target,
	cursorId,
	searchIndex,
}: getArticleSearchResultProps) => {
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

interface getArticleByHashTagProps {
	hashTags: string;
	cursorId: string;
}

export const getArticleByHashTag = async ({
	hashTags,
	cursorId = '',
}: getArticleByHashTagProps) => {
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

interface getSearchResultProps {
	target: string;
	searchIndex: string;
	cursorId: string;
}

export const getSearchResult = async ({
	target,
	searchIndex,
	cursorId = '',
}: getSearchResultProps) => {
	if (searchIndex === '유저') {
		const data = await getUserSearchResult({ target, searchIndex, cursorId });
		return data;
	}

	const data = await getArticleSearchResult({ target, searchIndex, cursorId });
	return data;
};
