import { PropsWithOptionalChildren } from 'gongseek-types';

import ArticleContent from '@/components/article/ArticleContent/ArticleContent';
import CommentContent from '@/components/comment/CommentContent/CommentContent';
import * as S from '@/pages/Detail/index.styles';
import { ArticleType } from '@/types/articleResponse';
import { CommentType } from '@/types/commentResponse';

export interface DetailProps {
	article: ArticleType;
	commentList: CommentType[];
	articleId: string;
	category: string;
}

const Detail = ({
	children,
	article,
	commentList,
	articleId,
	category,
}: PropsWithOptionalChildren<DetailProps>) => (
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

export default Detail;
