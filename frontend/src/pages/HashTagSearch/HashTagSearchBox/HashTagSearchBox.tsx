import { useState } from 'react';

import * as S from '@/pages/HashTagSearch/HashTagSearchBox/HashTagSearchBox.styles';

export interface HashTagSearchBoxProps {
	targets: { name: string; isChecked: boolean }[];
	setTargets: React.Dispatch<React.SetStateAction<{ name: string; isChecked: boolean }[]>>;
}

const HashTagSearchBox = ({ targets, setTargets }: HashTagSearchBoxProps) => {
	const [isOpen, setIsOpen] = useState(false);

	const onTargetClick = (target: string) => {
		setTargets(
			targets.map((item) => {
				if (item.name === target) {
					return {
						name: item.name,
						isChecked: !item.isChecked,
					};
				}
				return {
					name: item.name,
					isChecked: item.isChecked,
				};
			}),
		);
	};

	return (
		<S.Container>
			<h2 hidden>해시태그들을 보여주는 곳입니다</h2>
			<S.HashTagLists aria-label="검색하고 싶은 해시태그들을 클릭해주세요">
				{targets.length >= 5
					? targets.slice(0, 5).map((item) => (
							<S.HashTagItem
								key={item.name}
								isChecked={item.isChecked}
								onClick={() => onTargetClick(item.name)}
							>
								{item.name}
							</S.HashTagItem>
					  ))
					: targets.map((item) => (
							<S.HashTagItem
								key={item.name}
								isChecked={item.isChecked}
								onClick={() => onTargetClick(item.name)}
							>
								{item.name}
							</S.HashTagItem>
					  ))}
				{isOpen &&
					targets.slice(5).map((item) => (
						<S.HashTagItem
							key={item.name}
							isChecked={item.isChecked}
							onClick={() => onTargetClick(item.name)}
						>
							{item.name}
						</S.HashTagItem>
					))}

				{targets.length >= 5 && !isOpen && (
					<S.HashTagButton
						onClick={() => {
							setIsOpen(!isOpen);
						}}
					>
						더보기
					</S.HashTagButton>
				)}
				{isOpen && <S.HashTagButton onClick={() => setIsOpen(!isOpen)}>닫기</S.HashTagButton>}
			</S.HashTagLists>
		</S.Container>
	);
};

export default HashTagSearchBox;
