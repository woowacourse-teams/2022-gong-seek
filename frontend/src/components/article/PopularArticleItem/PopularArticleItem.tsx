import { useEffect, useRef } from 'react';

import Card from '@/components/@common/Card/Card';
import * as S from '@/components/article/ArticleItem/ArticleItem.styles';
import * as PopularS from '@/components/article/PopularArticleItem/PopularArticleItem.styles';
import { CAROUSEL_AUTO_PLAY_TIME } from '@/constants/index';
import { PopularArticleItemCardStyle } from '@/styles/cardStyle';
import { CommonArticleType } from '@/types/articleResponse';
import { convertGithubAvatarUrlForResize, dateTimeConverter } from '@/utils/converter';

const PopularArticleItem = ({
	article,
	isActive,
	onClick,
	rightSlide,
}: {
	article: CommonArticleType;
	isActive: boolean;
	onClick?: () => void;
	rightSlide?: () => void;
}) => {
	const { title, author, commentCount, views, createdAt, tag, category, likeCount } = article;
	const timerId = useRef<number | undefined>(undefined);
	const 한글_카테고리 = category === 'question' ? '질문' : '토론';

	useEffect(() => {
		if (isActive && rightSlide) {
			timerId.current = window.setTimeout(rightSlide, CAROUSEL_AUTO_PLAY_TIME);
		}

		return () => {
			window.clearTimeout(timerId.current);
		};
	}, [isActive, rightSlide]);

	return (
		<>
			<Card {...PopularArticleItemCardStyle} isActive={isActive} onClick={onClick} role="tab">
				<PopularS.PopularArticleHeader category={category}>
					<S.ProfileBox>
						<PopularS.PopularArticleUserProfile
							src={convertGithubAvatarUrlForResize(author.avatarUrl)}
							alt="프로필 이미지"
						/>
						<PopularS.AuthorNameText>{author.name}</PopularS.AuthorNameText>
						<PopularS.CategoryBox categoryType={category}>{한글_카테고리}</PopularS.CategoryBox>
					</S.ProfileBox>
				</PopularS.PopularArticleHeader>
				<PopularS.PopularArticleContent>
					<PopularS.PopularArticleItemTitle>
						<div>{title}</div>
					</PopularS.PopularArticleItemTitle>
					<S.ArticleInfoBox>
						<S.ArticleTimeStamp>{dateTimeConverter(createdAt)}</S.ArticleTimeStamp>
						<S.ArticleInfoSubBox>
							<PopularS.IconContainer>
								<PopularS.CommentIcon aria-label="댓글수" />
								{commentCount.toLocaleString()}
							</PopularS.IconContainer>
							<PopularS.IconContainer>
								<PopularS.ViewIcon aria-label="조회수" />
								{views.toLocaleString()}
							</PopularS.IconContainer>
							<PopularS.IconContainer>
								<PopularS.HeartIcon aria-label="좋아요수" />
								{likeCount.toLocaleString()}
							</PopularS.IconContainer>
						</S.ArticleInfoSubBox>
					</S.ArticleInfoBox>

					<PopularS.PopularArticleHashTagListBox>
						{tag &&
							(tag.length >= 1 ? (
								tag.map((item) => (
									<S.HashTagItem key={item}>
										<span aria-label="해시태그">#</span>
										{item}
									</S.HashTagItem>
								))
							) : (
								<div>&nbsp;</div>
							))}
					</PopularS.PopularArticleHashTagListBox>
				</PopularS.PopularArticleContent>
			</Card>
		</>
	);
};

export default PopularArticleItem;
