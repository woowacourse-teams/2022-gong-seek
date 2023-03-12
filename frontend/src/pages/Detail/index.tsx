import { PropsWithOptionalChildren } from 'gongseek-types';

import { ArticleTotalType } from '@/api/article/articleType';
import { SingleCommentItemType } from '@/api/comment/commentType';
import ArticleContent from '@/components/article/ArticleContent/ArticleContent';
import CommentContent from '@/components/comment/CommentContent/CommentContent';
import useScrollToTop from '@/hooks/common/useScrollToTop';
import * as S from '@/pages/Detail/index.styles';

export interface DetailProps {
	article: Omit<ArticleTotalType, 'updatedAt' | 'category' | 'commentCount'>;
	commentList: SingleCommentItemType[];
	articleId: string;
	category: string;
}

const Detail = ({
	children,
	article,
	commentList,
	articleId,
	category,
}: PropsWithOptionalChildren<DetailProps>) => {
	useScrollToTop();

	return (
		<S.Container>
			<ArticleContent
				article={article}
				author={article.author}
				category={category}
				articleId={articleId}
			/>
			{children}
			<CommentContent articleId={articleId} commentList={commentList} />
		</S.Container>
	);
};

export default Detail;
