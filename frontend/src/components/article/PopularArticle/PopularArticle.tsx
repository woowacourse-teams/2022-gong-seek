import EmptyMessage from '@/components/@common/EmptyMessage/EmptyMessage';
import Loading from '@/components/@common/Loading/Loading';
import * as S from '@/components/article/PopularArticle/PopularArticle.styles';
import PopularArticleItem from '@/components/article/PopularArticleItem/PopularArticleItem';
import useGetPopularArticles from '@/hooks/queries/article/useGetPopularArticles';
import { convertIdxToArticleColorKey } from '@/utils/converter';

const PopularArticle = () => {
	const {
		data,
		isLoading,
		isIdle,
		currentIndex,
		handleLeftSlideEvent,
		handleRightSlideEvent,
		mainArticleContent,
	} = useGetPopularArticles();

	if (isLoading || isIdle) {
		return <Loading />;
	}

	const getColorKey = (index: number) => {
		if (index < 0) {
			return convertIdxToArticleColorKey(9);
		}
		if (index > 9) {
			return convertIdxToArticleColorKey(0);
		}
		return convertIdxToArticleColorKey(index);
	};

	if (!data?.articles.length) {
		return <EmptyMessage>게시글이 존재하지 않습니다</EmptyMessage>;
	}

	return data ? (
		<S.Container>
			<S.LeftArrowButton onClick={handleLeftSlideEvent} />
			<S.LeftBackgroundArticle colorKey={getColorKey(currentIndex - 1)} />
			<S.ArticleContent colorKey={getColorKey(currentIndex)} ref={mainArticleContent}>
				<PopularArticleItem article={data.articles[currentIndex]} />
			</S.ArticleContent>
			<S.RightBackgroundArticle colorKey={getColorKey(currentIndex + 1)} />
			<S.RightArrowButton onClick={handleRightSlideEvent} />
		</S.Container>
	) : null;
};

export default PopularArticle;
