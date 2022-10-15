import { useNavigate } from 'react-router-dom';

import Card from '@/components/@common/Card/Card';
import * as S from '@/components/article/ArticleItem/ArticleItem.styles';
import { PopularArticleItemCardStyle } from '@/styles/cardStyle';
import { CommonArticleType } from '@/types/articleResponse';
import { convertGithubAvatarUrlForResize, dateTimeConverter } from '@/utils/converter';

const PopularArticleItem = ({
	article,
	isActive,
}: {
	article: CommonArticleType;
	isActive: boolean;
}) => {
	const navigate = useNavigate();
	const { id, title, author, commentCount, views, likeCount, category } = article;

	const handleClickTitle = () => {
		navigate(`/articles/${category}/${id}`);
	};
	return (
		<>
			<Card {...PopularArticleItemCardStyle} isActive={isActive}>
				<S.ArticleItemTitle>
					<div>{article.title}</div>
				</S.ArticleItemTitle>
				<S.ArticleInfoBox>
					<S.ArticleTimeStamp>{dateTimeConverter(article.createdAt)}</S.ArticleTimeStamp>
					<S.ArticleInfoSubBox>
						<S.CommentCount>댓글 수 {article.commentCount}</S.CommentCount>
						<S.Views>조회 수 {article.views}</S.Views>
					</S.ArticleInfoSubBox>
				</S.ArticleInfoBox>
				<S.HashTagListBox>
					{article.tag &&
						article.tag.length >= 1 &&
						article.tag.map((item) => <S.HashTagItem key={item}>#{item}</S.HashTagItem>)}
				</S.HashTagListBox>
				<S.FooterBox>
					<S.ProfileBox>
						<S.UserProfile src={convertGithubAvatarUrlForResize(article.author.avatarUrl)} />
						<div>{article.author.name}</div>
					</S.ProfileBox>
					<S.RightFooterBox></S.RightFooterBox>
				</S.FooterBox>
			</Card>
		</>
	);
};

export default PopularArticleItem;
