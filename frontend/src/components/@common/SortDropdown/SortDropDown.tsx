import { useEffect, useState } from 'react';

import * as S from '@/components/@common/SortDropdown/SortDropdown.styles';
import { SrOnlyContainer } from '@/components/user/UserProfileIcon/UserProfileIcon.styles';

export interface SortPropDownProps {
	sortList: string[];
	sortIndex: string;
	setSortIndex: React.Dispatch<React.SetStateAction<string>>;
}

const SortDropdown = ({ sortList, sortIndex, setSortIndex }: SortPropDownProps) => {
	const [onDropdown, setOnDropdown] = useState(false);
	const [isShowAlertLog, setIsShowAlertLog] = useState(false);

	const handleClickSortBox = () => {
		setOnDropdown((prevOnDropdown) => !prevOnDropdown);
	};

	const handleClickDropdownItem = (sort: string) => {
		setSortIndex(sort);
		setOnDropdown(false);
	};

	useEffect(() => {
		if (isShowAlertLog) {
			setIsShowAlertLog(false);
		}
	}, [isShowAlertLog]);

	const handleSortDropdownKeydown = (e: React.KeyboardEvent<HTMLDivElement>) => {
		if (e.key === 'Escape') {
			setIsShowAlertLog(true);
			setOnDropdown(false);
		}
	};

	return (
		<S.Container onKeyDown={handleSortDropdownKeydown}>
			<S.SortBox
				onClick={handleClickSortBox}
				tabIndex={0}
				aria-label={`게시물 정렬 드롭다운 선택된 상태 ${sortIndex}`}
			>
				<div>{sortIndex}</div>
				<S.ArrowDown />
			</S.SortBox>

			{onDropdown && (
				<S.DropdownBox role="tablist">
					{sortList.map((sort, idx) => (
						<S.DropdownItem
							key={idx}
							idx={idx}
							onClick={() => handleClickDropdownItem(sort)}
							role="tab"
							tabIndex={0}
						>
							{sort}
						</S.DropdownItem>
					))}
				</S.DropdownBox>
			)}
			{isShowAlertLog && <SrOnlyContainer role="alert">닫힘</SrOnlyContainer>}
		</S.Container>
	);
};

export default SortDropdown;
